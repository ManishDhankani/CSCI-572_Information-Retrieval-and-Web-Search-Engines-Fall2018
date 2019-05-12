import java.util.Set;
import java.util.regex.Pattern;


import org.apache.http.HttpStatus;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import au.com.bytecode.opencsv.*;
import java.io.FileWriter;
import au.com.bytecode.opencsv.CSVWriter;


public class MyCrawler extends WebCrawler {
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css||css?|js"  + "|mp3|mp4|zip|gz|json))$");

	
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		 String href = url.getURL().toLowerCase();
		 
		 boolean sv;
		 
		 sv =  !FILTERS.matcher(href).matches()
		 && (href.startsWith("https://www.cnn.com/") || href.startsWith("https://www.cnn.com/"));
		 
		 
		 
		 try {
			 String urlsCSV = "urls_CNN.csv";
			 CSVWriter urlsWriter = new CSVWriter(new FileWriter(urlsCSV, true));
			 if(sv) {
				 urlsWriter.writeNext(new String[] {href, "OK"});
			 }else {
				 urlsWriter.writeNext(new String[] {href, "N_OK"});				 
			 }
			 
			 urlsWriter.close();
		 }catch(Exception e) {
			 System.out.println("Error when writing to urls file");
		 }
		 
		 return sv;
	}
	
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
		String url = webUrl.getURL();
		
		try { 
			String fetchCSV = "fetch_CNN.csv";
		    CSVWriter fetchWriter = new CSVWriter(new FileWriter(fetchCSV, true));
		    fetchWriter.writeNext(new String[] {url, Integer.toString(statusCode)});
		    fetchWriter.close();
		}catch(Exception e) {
			
		}
    }
	
	
	@Override
	 public void visit(Page page) {
	
		String url = page.getWebURL().getURL();
		 int statusCode = page.getStatusCode();
		 String contentType = page.getContentType();
		 	 
		 if (page.getParseData() != null) {

			 Set<WebURL> links = page.getParseData().getOutgoingUrls();
			 int numberOfOutgoingLinks = links.size();
			 
			 try {
				 
				 String visitCSV = "visit_CNN.csv";
				 CSVWriter visitWriter = new CSVWriter(new FileWriter(visitCSV, true));
				 
				 int pageSize = page.getContentData().length;
				 visitWriter.writeNext(new String[] {url, Integer.toString(pageSize), Integer.toString(numberOfOutgoingLinks), contentType });
				 
				 visitWriter.close();
				 
			 }catch(Exception e) {
				 
			 }
		 }
	 }
}
