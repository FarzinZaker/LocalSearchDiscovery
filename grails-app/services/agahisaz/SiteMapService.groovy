package agahisaz

import grails.util.Environment

import java.text.DecimalFormat


class SiteMapService {

    def mongoService
    def grailsApplication

    private rootUrl

    private pageSize = 1000
    private fileSize = 20000

    private String filesPath

    private String DAILY = 'daily'
    private String WEEKLY = 'weekly'
    private String MONTHLY = 'monthly'
    private String YEARLY = 'yearly'

    def refresh() {
        filesPath = grailsApplication.config.sitemap.path
        rootUrl = grailsApplication.config.sitemap.rootUrl

        def list =
                refreshStatics() +
                        refreshPlaces() +
                        refreshCategories() +
                        refreshTags() +
                        refreshLocations()
        createIndex(list?.toList())
    }

    private void createIndex(List<String> list) {
        def tempFileName = "${filesPath}index.xml.tmp"
        def tempFile = new File(tempFileName)
        if (tempFile.exists()) {
            tempFile.delete()
            tempFile = new File(tempFileName)
        }
        tempFile.createNewFile()
        tempFile.append("""<?xml version="1.0" encoding="UTF-8"?>
<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
""", 'UTF-8')
        list.each { item ->
            tempFile.append("""\t<sitemap>
        <loc>${item}</loc>
        <lastmod>${prepareDate(new Date())}</lastmod>
    </sitemap>
""")
        }
        tempFile.append("</sitemapindex>")


        def fileName = "${filesPath}index.xml"
        def file = new File(fileName)
        if (file.exists())
            file.delete()
        tempFile.renameTo(fileName)
    }

    private List<String> refreshStatics() {
        createTempFile('static', 0)
        def items = [
                "http://www.agahisaz.com",
                "http://www.agahisaz.com/info/about",
                "http://www.agahisaz.com/info/help",
                "http://www.agahisaz.com/info/oldUsersHelp",
                "http://www.agahisaz.com/info/cookie",
                "http://www.agahisaz.com/info/privacy",
                "http://www.agahisaz.com/info/terms",
                "http://www.agahisaz.com/info/contact"
        ]
        items.eachWithIndex { item, index ->
            appendToTempFile('static', 0, getUrl(
                    item?.toString(),
                    new Date(),
                    DAILY,
                    1.0F

            ))
        }
        [replaceOriginalFile('static', 0)]
    }

    private List<String> refreshPlaces() {

        def sitemaps = new HashSet<String>()
        def totalCount = Place.countByReportTypeIsNull()
        def skip = 0;
        while (skip < totalCount) {
            createTempFile('place', skip)
            def items = mongoService.getCollection('place').aggregate(
                    [$match: ['reportType': [$eq: null]]],
                    [$skip: skip],
                    [$limit: pageSize]
            ).results()?.findAll()
            items.eachWithIndex { item, index ->
                appendToTempFile('place', skip, getUrl(
                        "http://${rootUrl}/place/view/${item._id}/${URLEncoder.encode(item.name?.toString(), 'UTF-8')}",
                        item.lastUpdated ?: new Date(),
                        DAILY,
                        0.9F

                ))
            }
            if (!((skip + pageSize) % fileSize) || (skip + pageSize) > totalCount)
                sitemaps << replaceOriginalFile('place', skip)
            skip += pageSize
        }

        sitemaps?.toList()?.sort()
    }

    private List<String> refreshCategories() {

        def sitemaps = new HashSet<String>()
        def totalCount = Category.count()
        def skip = 0;
        while (skip < totalCount) {
            createTempFile('category', skip)
            def items = mongoService.getCollection('category').aggregate(
                    [$skip: skip],
                    [$limit: pageSize]
            ).results()?.findAll()
            items.eachWithIndex { item, index ->
                appendToTempFile('category', skip, getUrl(
                        "http://${rootUrl}/place/explore/${URLEncoder.encode(item.name?.toString(), 'UTF-8')}",
                        item.lastUpdated ?: new Date(),
                        DAILY,
                        0.7F

                ))
            }
            if (!((skip + pageSize) % fileSize) || (skip + pageSize) > totalCount)
                sitemaps << replaceOriginalFile('category', skip)
            skip += pageSize
        }

        sitemaps?.toList()?.sort()
    }

    private List<String> refreshTags() {

        def sitemaps = new HashSet<String>()
        def totalCount = Tag.count()
        def skip = 0;
        while (skip < totalCount) {
            createTempFile('tag', skip)
            def items = mongoService.getCollection('tag').aggregate(
                    [$skip: skip],
                    [$limit: pageSize]
            ).results()?.findAll()
            items.eachWithIndex { item, index ->
                appendToTempFile('tag', skip, getUrl(
                        "http://${rootUrl}/place/explore/?tags=${URLEncoder.encode(item.name?.toString(), 'UTF-8')}",
                        item.lastUpdated ?: new Date(),
                        DAILY,
                        0.5F

                ))
            }
            if (!((skip + pageSize) % fileSize) || (skip + pageSize) > totalCount)
                sitemaps << replaceOriginalFile('tag', skip)
            skip += pageSize
        }

        sitemaps?.toList()?.sort()
    }

    private List<String> refreshLocations() {

        def sitemaps = new HashSet<String>()
        def totalCount = Province.count()
        def skip = 0;
        while (skip < totalCount) {
            createTempFile('city', skip)
            def items = mongoService.getCollection('province').aggregate(
                    [$skip: skip],
                    [$limit: pageSize]
            ).results()?.findAll()
            items.eachWithIndex { item, index ->
                item.cities.each { subItem ->
                    appendToTempFile('city', skip, getUrl(
                            "http://${rootUrl}/place/explore/?province=${URLEncoder.encode(item.name?.toString(), 'UTF-8')}&amp;city=${URLEncoder.encode(subItem.name?.toString(), 'UTF-8')}",
                            item.lastUpdated ?: new Date(),
                            DAILY,
                            0.3F
                    ))
                }
            }
            if (!((skip + pageSize) % fileSize) || (skip + pageSize) > totalCount)
                sitemaps << replaceOriginalFile('city', skip)
            skip += pageSize
        }

        sitemaps?.toList()?.sort()
    }

    private String getUrl(String location, Date lastModifyDate, String changeFrequency, Float priority) {
        """\t<url>
        <loc>${location}</loc>
        <lastmod>${prepareDate(lastModifyDate)}</lastmod>
        <changefreq>${changeFrequency}</changefreq>
        <priority>${new DecimalFormat("#.#").format(priority)}</priority>
    </url>"""
    }

    private String prepareDate(Date date) {
        def calendar = Calendar.getInstance()
        calendar.setTime(date)
        "${new DecimalFormat("0000").format(calendar.get(Calendar.YEAR))}-${new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1)}-${new DecimalFormat("00").format(calendar.get(Calendar.DAY_OF_MONTH))}"
    }

    private String getTempFilePath(String type, Integer skip) {
        "${filesPath}${type}-${(skip / fileSize).toInteger() + 1}.xml.tmp"
    }

    private String getFilePath(String type, Integer skip) {
        "${filesPath}${type}-${(skip / fileSize).toInteger() + 1}.xml"
    }

    private boolean createTempFile(String type, Integer skip) {
        if (skip % fileSize)
            return false
        def fileName = getTempFilePath(type, skip)
        def file = new File(fileName)
        if (file.exists()) {
            file.delete()
            file = new File(fileName)
        }
        file.createNewFile()
        file.append("""<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
""", 'UTF-8')
        true
    }

    private void appendToTempFile(String type, Integer skip, String url) {
        def fileName = getTempFilePath(type, skip)
        def file = new File(fileName)
        file.append(url + '\r\n', 'UTF-8')
    }

    private String replaceOriginalFile(String type, Integer skip) {
        def tempFileName = getTempFilePath(type, skip)
        def tempFile = new File(tempFileName)
        tempFile.append("</urlset>\r\n", 'UTF-8')
        def fileName = getFilePath(type, skip)
        def file = new File(fileName)
        if (file.exists())
            file.delete()
        tempFile.renameTo(fileName)
        "http://${rootUrl}/sitemap/${type}/${(skip / fileSize).toInteger() + 1}"
    }
}
