package app.umf.mziks1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {



    private EditText editText;
    private String test;
    private ArrayList<NoteObject> arrayList;
    private TextView textViewC,textViewH,textViewR;


    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         editText=(EditText) findViewById(R.id.editText);
         listView=(ListView) findViewById(R.id.list_item) ;
         arrayList=new ArrayList<>();
        textViewC=(TextView)findViewById(R.id.textView_C);
        textViewR=(TextView)findViewById(R.id.textView_R);
        textViewH=(TextView)findViewById(R.id.textView_H);

        Button button=(Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                test=editText.getText().toString();

                HashMap<Character,Integer> hashmap=new HashMap<Character, Integer>();
                for(char c:test.toCharArray())
                {
                    if(hashmap.containsKey(c)) {
                        hashmap.put(c, hashmap.get(c) + 1);
                    }
                    else {
                        hashmap.put(c,1);
                    }
                }
                // build tree
                HuffmanTree tree=buildTree(hashmap);

                toArrayList(tree, new StringBuffer());
                Collections.sort(arrayList, new Comparator<NoteObject>() {
                    @Override
                    public int compare(NoteObject noteObject, NoteObject t1) {
                        return noteObject.frequency-t1.frequency;
                    }
                });
                List<String> a=new ArrayList<String>();
                for (NoteObject o:arrayList) {
                    a.add(o.toString());
                }
                ArrayAdapter<String> aa=new ArrayAdapter<String>(getApplicationContext(),R.layout.my_list_item2,a);
                listView.setAdapter(aa);
                textViewH.setText(String.valueOf( calculateH()));
                textViewC.setText(String.valueOf( calculateC()));
                textViewR.setText(String.valueOf( calculateR()));
            }
        });



    }

    public  HuffmanTree buildTree(HashMap<Character,Integer> hashmap) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();

        ArrayList<Character> array=new ArrayList<>(hashmap.keySet());
        for(int i=0;i<hashmap.size();i++)
        {
            trees.offer(new HuffmanLeaf(hashmap.get(array.get(i)),array.get(i)));
        }

        assert trees.size() > 0;

        while (trees.size() > 1) {

            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();


            trees.offer(new HuffmanNode(a,b));
        }
        return trees.poll();
    }

    public  void toArrayList(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            arrayList.add(new NoteObject(leaf.value, leaf.frequency, prefix.toString()));


        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;


            prefix.append('0');
            toArrayList(node.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1);


            prefix.append('1');
            toArrayList(node.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public double calculateH()
    {
        double result=0;
        double totalFrequency=totalFrequency();
        for (NoteObject a:arrayList) {
            double pi=a.frequency/totalFrequency;
            result+=pi*(Math.log(pi)/Math.log(2));
        }
        return -result;
    }
    public double totalFrequency()
    {
        double totalFrequency=0;
        for(NoteObject a:arrayList)
            totalFrequency+=a.frequency;
        return totalFrequency;
    }

    public double calculateC()
    {
        double result=0;
        for(NoteObject a:arrayList)
        {
            double pi=a.frequency/totalFrequency();
            result+=pi*a.code.length();
        }
        return result;
    }

    public double calculateR()
    {
        return calculateC()-calculateH();
    }

}
