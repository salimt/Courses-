import java.util.ArrayList;

/**
 * @author: salimt
 */

public class MatchAllFilter implements Filter {

    private ArrayList<Filter> filterList;

    public MatchAllFilter() { filterList = new ArrayList <Filter>(); }

    public void addFilter(Filter f){ filterList.add(f); }

    public String getName(){
        ArrayList<String> filterNames = new ArrayList <>();
        for(Filter f: filterList){ filterNames.add(f.getName()); }
        return String.join(" ", filterNames);
    }

    public boolean satisfies(QuakeEntry qe) {
        boolean result = true;
        for(Filter f: filterList){ if(!f.satisfies(qe)){ return false; }  }
        return true;

    }

}
