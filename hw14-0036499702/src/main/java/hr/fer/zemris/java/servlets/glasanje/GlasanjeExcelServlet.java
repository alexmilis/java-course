package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that generates xls file (Excel workbook) with voting results.
 * This file is downloaded when this servlet is called.
 * Table is stored in sheet "results". 
 * First column is band name and second column is number of votes that band received.
 * @author Alex
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeExcelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long pollID = Long.parseLong(req.getParameter("id"));
		List<PollOption> results = DAOProvider.getDao().getPollOption(pollID, true);
		
		if(results.isEmpty()) {
			req.setAttribute("error", "Poll with id " + pollID + " is empty!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("rezultati");
		
		HSSFRow rowhead = sheet.createRow((short)0);
		rowhead.createCell((short) 0).setCellValue("Opcija");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");
		
		int counter = 1;
		for(PollOption option : results) {
			HSSFRow row = sheet.createRow((short) (counter++));
			row.createCell((short) 0).setCellValue(option.getOptionName());
			row.createCell((short) 1).setCellValue(option.getVotes());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "rezultati.xls");
		
		try {
			hwb.write(resp.getOutputStream());
			hwb.close();
		} catch (IOException ex){
			req.setAttribute("error", "Unable to download excel file!");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}
}
