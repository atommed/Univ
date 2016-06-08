/**
 * Created by gregory on 08.06.16.
 */

import io.github.atommed.bst.BSTree;

public class Program {
    public static void testTree(BSTree<Integer, String> tree){
        System.out.println("Traversing");
        tree.traverse();
        System.out.println("Search");
        System.out.println("Search 42 is \""+tree.get(42)+"\"");
        System.out.println("Search 18 is \""+tree.get(18)+"\"");
    }

    public static void pp(String str){
        final String att = "######################################";
        int nOts = (att.length() - str.length() - 2)/2;
        String sps = "";
        for(int i = 0; i < nOts; i++)
            sps+="#";
        System.out.println(att);
        System.out.println(sps+" "+str+" "+sps);
        System.out.println(att);
    }

    public static void main(String... args){
        BSTree<Integer,String> tree;

        tree = new BSTree<>();
        //System.out.println("Empty tree");
        pp("Empty tree");
        testTree(tree);


        pp("Tree with elements");
        tree = new BSTree<>();
        int els[] = {22, 16, 36, 14, 11, 4, 18, 17, 44, 38, 43, 37, 3, 50, 25};
        for(int i : els){
            tree.insert(i,Integer.toString(i));
        }
        testTree(tree);

        tree.removeAll(new BSTree.RemoveCriteria<Integer, String>() {
            @Override
            public boolean toRemove(Integer key, String value) {
                return  key.compareTo(15) < 0;
            }
        });

        tree.insert(42,"lol");
        pp("Traversing after deleted all < 15");
        tree.traverse();
    }
}
