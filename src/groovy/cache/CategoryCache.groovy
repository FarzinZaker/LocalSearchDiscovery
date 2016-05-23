package cache

import agahisaz.Category

/**
 * Created by Farzin on 4/30/2016.
 */
class CategoryCache {

    private static HashMap<Long, Map> _categories;
    private static List<Map> _rootCategories;

    private static void initCategoryData() {
        def items = Category.findAll()
        items.each { category ->
            def parent = category.parent
            while (parent) {
                category.parentIdList << parent.id
                parent.childIdList << category.id
                parent = parent?.parent
            }
        }
        _categories = new HashMap<Long, Map>()
        _rootCategories = []
        items.each { category ->
            def cat = [
                    id               : category.id,
                    name             : category.name,
                    pluralName       : category.pluralName,
                    englishName      : category.englishName,
                    englishPluralName: category.englishPluralName,
                    iconPath         : category.iconPath,
                    parentId         : category.parentId,
                    childIdList      : category.childIdList.clone(),
                    parentIdList     : category.parentIdList.clone()
            ]
            _categories.put(category.id, cat)

            if(category.parentIdList.isEmpty()){
                _rootCategories << cat
            }
        }
    }

    public static HashMap<Long, Map> getCategories() {
        if (!_categories)
            initCategoryData()
        _categories
    }

    public static List<Map> getRootCategories() {
        if (!_rootCategories)
            initCategoryData()
        _rootCategories
    }

    public static Map findCategory(id) {
        categories[id as Long]
    }
}
