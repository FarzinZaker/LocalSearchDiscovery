package utils

/**
 * Created by Farzin on 4/18/2016.
 */
class StringHelper {

    public final static String normalize(String input) {

        normalizeInternal(input, input.size())
    }

    private final static String normalizeInternal(def input, def len)
    {
        def s = input.toString().toCharArray()
        for(int i = 0; i < len; i++) {
            switch((int)s[i])
            {
                case 1610:
                    s[i] = '\u06cc'
                    break
                case 1746:
                    s[i] = '\u06cc'
                    break

                case 1603:
                    s[i] = '\u06A9'
                    break

                case 1728:
                    s[i] = '\u0647'
                    break
                case 1729:
                    s[i] = '\u0647'
                    break

                case 1620:
                    len = delete(s, i, len)
                    i--
                    break
            }
        }
        s.toString()
    }

    private static int delete(def s, int pos, int len)
    {
        if(pos < len)
            System.arraycopy(s, pos + 1, s, pos, len - pos - 1)
        len - 1
    }
}
