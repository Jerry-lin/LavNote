package cn.edu.hust.hbase;

import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.utils.Constant;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HBaseTest{

    public static void main(String[] args) throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/applicationContext*.xml"});

        BeanFactory factory = (BeanFactory) context;

        HbaseTemplate hbaseTemplate = (HbaseTemplate) factory.getBean("hbaseTemplate");


        List<NoteBooks> noteBooksList=getNoteBooks(hbaseTemplate);


        for (NoteBooks noteBooks:noteBooksList)
        {
            System.out.println(noteBooks);
        }

    }

    private static List<NoteBooks> getNoteBooks(HbaseTemplate hbaseTemplate) throws IOException {
        final List<NoteBooks> noteBooksList = new LinkedList<>();
        hbaseTemplate.execute(Constant.NOTEBOOKS_NAME,new TableCallback<List<Result>>(){
            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                //RegexStringComparator rc = new RegexStringComparator("16_0a7a943c0d814d5283bc89f94e510619");
                //RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, rc);
                PrefixFilter rowFilter=new PrefixFilter("16_".getBytes());
                Scan scan = new Scan();
                scan.setFilter(rowFilter);
                ResultScanner resultScanner = table.getScanner(scan);
                for(Result rs:resultScanner)
                {
                    //Cell cell=rs.getColumnLatestCell("Info1".getBytes(),"NotebookId".getBytes());
                    //System.out.println(new String(cell.getValue()));

                    NoteBooks noteBooks=new NoteBooks();
                    String NoteBookId=new String(rs.getColumnLatestCell("Info1".getBytes(),"NotebookId".getBytes()).getValue());
                    String ParenNotebookId=new String(rs.getColumnLatestCell("Info1".getBytes(),"ParentNotebookId".getBytes()).getValue());
                    int Seq=Integer.parseInt(new String(rs.getColumnLatestCell("Info1".getBytes(),"Seq".getBytes()).getValue()));
                    String Title=new String(rs.getColumnLatestCell("Info1".getBytes(),"Title".getBytes()).getValue());
                    String UrlTitle=new String(rs.getColumnLatestCell("Info1".getBytes(),"UrlTitle".getBytes()).getValue());
                    int numberNotes=Integer.parseInt(new String(rs.getColumnLatestCell("Info1".getBytes(),"NumberNotes".getBytes()).getValue()));
                    boolean isTrash=Integer.parseInt(new String(rs.getColumnLatestCell("Info2".getBytes(),"IsTrash".getBytes()).getValue()))==0?false:true;
                    boolean isBlog=Integer.parseInt(new String(rs.getColumnLatestCell("Info2".getBytes(),"IsBlog".getBytes()).getValue()))==0?false:true;
                    String CreatedTime=new String(rs.getColumnLatestCell("Info2".getBytes(),"CreatedTime".getBytes()).getValue());
                    String UpdatedTime=new String(rs.getColumnLatestCell("Info2".getBytes(),"UpdatedTime".getBytes()).getValue());
                    boolean IsWX=Integer.parseInt(new String(rs.getColumnLatestCell("Info2".getBytes(),"IsWX".getBytes()).getValue()))==0?false:true;
                    int Usn=Integer.parseInt(new String(rs.getColumnLatestCell("Info2".getBytes(),"Usn".getBytes()).getValue()));
                    boolean IsDeleted=Integer.parseInt(new String(rs.getColumnLatestCell("Info2".getBytes(),"IsDeleted".getBytes()).getValue()))==0?false:true;
                    //笔记本的子文件以json的格式存放在父类中
                    String Subs[]=new String(rs.getColumnLatestCell("Info2".getBytes(),"Subs".getBytes()).getValue()).split(",");
                    List<String> subs=new LinkedList<>();
                    for(int i=0;i<Subs.length;i++)
                    {
                        subs.add(Subs[i]);
                    }
                    noteBooks.set("16",NoteBookId,ParenNotebookId,Seq,Title,UrlTitle,numberNotes,isTrash,isBlog,CreatedTime,UpdatedTime,IsWX,Usn,IsDeleted,subs);
                    noteBooksList.add(noteBooks);

                    /*
                    for(KeyValue kv:rs.raw())
                    {
                       System.out.println(kv.toString());
                    }*/
                }
                return null;

            }

        });
        return noteBooksList;
        /*
        if(list.size()==0) return null;

        List<NoteBooks> noteBooksList=new ArrayList<>();
        for(Result result:list)
        {

            NoteBooks noteBooks=new NoteBooks();
            String NoteBookId=new String(result.getValue("Info1".getBytes(),"NoteBookId".getBytes()));
            String ParenNotebookId=new String(result.getValue("Info1".getBytes(),"ParenNotebookId".getBytes()));
            int Seq=Integer.parseInt(new String(result.getValue("Info1".getBytes(),"Seq".getBytes())));
            String Title=new String(result.getValue("Info1".getBytes(),"Title".getBytes()));
            String UrlTitle=new String(result.getValue("Info1".getBytes(),"UrlTitle".getBytes()));
            int numberNotes=Integer.parseInt(new String(result.getValue("Info1".getBytes(),"NumberNotes".getBytes())));
            boolean isTrash=Integer.parseInt(new String(result.getValue("Info2".getBytes(),"IsTrash".getBytes())))==0?false:true;
            boolean isBlog=Integer.parseInt(new String(result.getValue("Info2".getBytes(),"IsBlog".getBytes())))==0?false:true;
            String CreatedTime=new String(result.getValue("Info2".getBytes(),"CreatedTime".getBytes()));
            String UpdatedTime=new String(result.getValue("Info2".getBytes(),"UpdatedTime".getBytes()));
            boolean IsWX=Integer.parseInt(new String(result.getValue("Info2".getBytes(),"IsWX".getBytes())))==0?false:true;
            int Usn=Integer.parseInt(new String(result.getValue("Info2".getBytes(),"Usn".getBytes())));
            boolean IsDeleted=Integer.parseInt(new String(result.getValue("Info2".getBytes(),"IsDeleted".getBytes())))==0?false:true;
            //笔记本的子文件以json的格式存放在父类中
            String Subs[]=new String(result.getValue("Info2".getBytes(),"Subs".getBytes())).split(",");
            List<String> subs=new LinkedList<>();
            for(int i=0;i<Subs.length;i++)
            {
                subs.add(Subs[0]);
            }
            noteBooks.set("16",NoteBookId,ParenNotebookId,Seq,Title,UrlTitle,numberNotes,isTrash,isBlog,CreatedTime,UpdatedTime,IsWX,Usn,IsDeleted,subs);
            noteBooksList.add(noteBooks);
        }
        return noteBooksList;
        */
    }

    private static void getTitle(HbaseTemplate hbaseTemplate) {
        //hbaseTemplate.put("User","1","info","name","张三".getBytes());
        // 通过表名和rowKey获取最近一行数据
        String result = hbaseTemplate.get("NoteBooks", "16_0a7a943c0d814d5283bc89f94e510619", new RowMapper<String>() {
            public String mapRow(Result result, int rowNum) throws Exception {
                return Bytes.toString(result.getValue("Info1".getBytes(), "Title".getBytes()));
            }
        });
        System.out.println(result);
    }
}
