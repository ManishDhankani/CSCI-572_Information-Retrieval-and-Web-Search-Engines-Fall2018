import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.FileWriter;
import au.com.bytecode.opencsv.CSVWriter;



public class Controller {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String crawlStorageFolder = "/Users/meena/CSCI_Information_Retrieval/HW2/data/crawl";
		int numberOfCrawlers = 7;
		
		String fetchCSV = "fetch_CNN.csv";
		String visitCSV = "visit_CNN.csv";
		String urlsCSV = "urls_CNN.csv";
		
		CSVWriter fetchWriter = new CSVWriter(new FileWriter(fetchCSV));
		CSVWriter visitWriter = new CSVWriter(new FileWriter(visitCSV));
		CSVWriter urlsWriter = new CSVWriter(new FileWriter(urlsCSV));
		
		String[] fetchRecord = {"URL", "Status_Code"};
		String[] visitRecord = {"URL", "Size", "Number of outlinks", "Content Type"};
		String[] urlRecord = {"Encoutered URL", "Consideration Status"};
		
		fetchWriter.writeNext(fetchRecord);
		fetchWriter.close();
		
		visitWriter.writeNext(visitRecord);
		visitWriter.close();
		
		urlsWriter.writeNext(urlRecord);
		urlsWriter.close();
		
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(16);
		config.setMaxPagesToFetch(20000);
		config.setIncludeHttpsPages(true);
		config.setIncludeBinaryContentInCrawling(true);
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		controller.addSeed("https://www.cnn.com/");
		
		controller.start(MyCrawler.class, numberOfCrawlers);
	}

}
