package vn.edu.usth.arxiv;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class APIHandle {
    public static ArrayList<mDocument> docs = new ArrayList<>();
    public void getDocument(String dataxml){
        docs.clear();
        processData(dataxml);
    }

    public void loadMore(String dataxml){
        processData(dataxml);
    }

    public void loadFavs(String dataxml){
        try{
            mDocument doc = new mDocument();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(dataxml));
            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG){
//                    Log.i("START_TAG", " LINE :"+parser.getName());
                    if (parser.getName().equals("entry")){
                        doc = new mDocument();
                    } else if (parser.getName().equals("id")) {
                        doc.setId(parser.nextText().replace("http://arxiv.org/abs/",""));
                    } else if (parser.getName().equals("updated")) {
                        doc.setDate(parser.nextText());
                    } else if (parser.getName().equals("title")) {
                        doc.setTitle(parser.nextText());
                    } else if (parser.getName().equals("summary")) {
                        doc.setContent(parser.nextText());
                    } else if (parser.getName().equals("name")) {
                        doc.addAuthor(parser.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
//                    Log.i("END_TAG", parser.getName());
                    if (parser.getName().equals("entry")){
                        FavoriteFragment.docsFav.add(doc);
                    }
                }
                parser.next();
                eventType = parser.getEventType();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }


    private void processData(String dataxml){
        try{
            mDocument doc = new mDocument();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(dataxml));
            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG){
//                    Log.i("START_TAG", " LINE :"+parser.getName());
                    if (parser.getName().equals("entry")){
                        doc = new mDocument();
                    } else if (parser.getName().equals("id")) {
                        doc.setId(parser.nextText());
                    } else if (parser.getName().equals("updated")) {
                        doc.setDate(parser.nextText());
                    } else if (parser.getName().equals("title")) {
                        doc.setTitle(parser.nextText());
                    } else if (parser.getName().equals("summary")) {
                        doc.setContent(parser.nextText());
                    } else if (parser.getName().equals("name")) {
                        doc.addAuthor(parser.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
//                    Log.i("END_TAG", parser.getName());
                    if (parser.getName().equals("entry")){
                        docs.add(doc);
                    }
                }
                parser.next();
                eventType = parser.getEventType();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

}
