package cn.edu.hust.service.note.impl;

import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.service.note.NoteBooksService;
import cn.edu.hust.utils.Constant;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Service;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class NoteBooksServiceImpl implements NoteBooksService
{
    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Override
    public void addNoteBook(final String rowKey, final String tableName,final String[] family,final String[][] column, final String[][] value) {
        hbaseTemplate.execute(tableName, new TableCallback<String>() {
            @Override
            public String doInTable(HTableInterface table) throws Throwable {

                Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
                for(int i=0;i<family.length;i++)
                for (int j = 0; j < column[i].length; j++) {
                    put.add(Bytes.toBytes(family[i]), Bytes.toBytes(column[i][j]), Bytes.toBytes(value[i][j]));
                }
                table.put(put);
                return "ok";
            }

        });
    }

    /**
     * 1.先查询redis，如果redis内部没有，那么将会访问Hbase
     * 2.在HBase查询后，将内容保存在redis中
     * 3.如果出现错误，直接返回null
     * @param regxKey
     * @return
     */
    @Override
    public List<NoteBooks> findByRowKey(final String regxKey) {
        List<NoteBooks> noteBooksList=null;
        try
        {
            noteBooksList = getNoteBooks(hbaseTemplate,regxKey);
        }
        catch(Exception e)
        {
            return null;
        }
        return noteBooksList;
    }

    //封装查询出来的笔记本
    private  List<NoteBooks> getNoteBooks(HbaseTemplate hbaseTemplate,final String regexKey) throws IOException {
        final List<NoteBooks> noteBooksList = new LinkedList<>();
        hbaseTemplate.execute(Constant.NOTEBOOKS_NAME,new TableCallback<List<Result>>(){
            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                //RegexStringComparator rc = new RegexStringComparator("16_0a7a943c0d814d5283bc89f94e510619");
                //RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, rc);
                PrefixFilter rowFilter=new PrefixFilter((regexKey+"_").getBytes());
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
                    noteBooks.set(regexKey,NoteBookId,ParenNotebookId,Seq,Title,UrlTitle,numberNotes,isTrash,isBlog,CreatedTime,UpdatedTime,IsWX,Usn,IsDeleted,subs);
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
    }
}
