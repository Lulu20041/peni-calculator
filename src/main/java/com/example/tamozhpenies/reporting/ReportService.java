package com.example.tamozhpenies.reporting;

import com.example.tamozhpenies.peni.PeniService;
import com.example.tamozhpenies.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private final UserRepository userRepository;
    private final PeniService peniService;

    public ReportService(UserRepository userRepository, PeniService peniService) {
        this.userRepository = userRepository;
        this.peniService = peniService;
    }
    //Генерация отчёта
    public void exportClientReport(String username, HttpServletResponse response) throws IOException, JRException {

        File reportTemplate = ResourceUtils.getFile("classpath:report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(peniService.getAllByUsername(username));
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);

        double totalPeni = peniService.peniSum(username);
        params.put("totalPeni", totalPeni);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params,dataSource);

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    }
}
