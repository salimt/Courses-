/**
 * @author: salimt
 */

public class PhraseFilter implements Filter {

    private String where;
    private String phrase;

    public PhraseFilter(String keyword, String word) { where=keyword; phrase=word; }

    public String getName(){ return "Phrase"; }

    public boolean satisfies(QuakeEntry qe) {
        String title = qe.toString().substring(qe.toString().indexOf("title =")+8);
        if (where.equals("start")){ return title.startsWith(phrase); }
        if (where.equals("end")){ return title.endsWith(phrase); }
        else return title.contains(phrase);
        }
}
