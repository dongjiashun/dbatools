package com.yh.spring.ssm.service.sendmail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yh.spring.ssm.bean.SlowSqlForMail;
import com.yh.spring.ssm.bean.SqlwhiteList;
import com.yh.spring.ssm.bean.TableSize;
import com.yh.spring.ssm.bean.serverdisk.MysqlServerDisk;
import com.yh.spring.ssm.sendMail.MailUtils;
import com.yh.spring.ssm.service.rdsfuntion.iservice.RdsSlowSqlServiceImpl;
import com.yh.spring.ssm.service.rdsfuntion.iservice.RdsWhiteListDbServiceImpl;
import com.yh.spring.ssm.util.DateUtil;

/**
 * 用户发送邮件
 * @author dongjs
 *
 */
@SuppressWarnings({ "unused" })
public class MysqlDiskSendMailService {
	private static Logger logger = Logger.getLogger(MysqlDiskSendMailService.class);
//	private ApplicationContext ac = null;
 
	private static int a = 1;
	private String nowtime =DateUtil.formatDate(new Date());
//	private Map<String, String> depCodeNameMap = new HashMap<>();

	//2.添加到html模板里
	
	public void infoAddHtml(List<MysqlServerDisk> data) {

        try {
//---------------------------html 抬头-----------------------------------------------
            long endOfYesterday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
            long startOfYesterday = endOfYesterday - 24 * 60 * 60 * 1000;

            StringBuilder htmlContent = new StringBuilder("<html><body>");
            htmlContent.append("<style>\n" +
                "table {\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "table, td, th {\n" +
                "    border: 1px solid black;\n" +
                "}\n" +
                "td {\n" +
                "   padding-left:10px;\n" +
                "   word-wrap:break-word;word-break:break-all;\n" +
                "}\n" +
                "th{\n" +
                "   align:center;\n" +
                "}\n" +
                "</style>");
            htmlContent.append("<div style=\"padding-left:30px\">");
            htmlContent.append("<h2>数据库实例磁盘空间统计日报-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "</h2>");

            htmlContent.append("<h3>主要内容</h3>");
            htmlContent.append("<ul>");
            htmlContent.append("<li>统计汇总</li>");
//            htmlContent.append("<li>磁盘大于百分之50 Top 50</li>");
            htmlContent.append("</ul>");
            htmlContent.append("<h3><strong>数据库实例空间汇总</strong></h3>\n" +
                "<table style=\"border-color: 1px;width:350px;\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
//                "<th width=\"150px\">表空间</th>\n" +
//                "<th  width=\"200px\">单位G/w</th>\n" +
                "</tr>");
//            TreeMap<Long, String> depMap = getSqlSumByDep(startOfYesterday, endOfYesterday);
//--------------------------------表空间统计明细------------------------------------------------
               String nowtime= DateUtil. formatDate(new Date());
               //获取mail所需的数据
            htmlContent.append("<h3><strong>显示实例维度的数据库实例</strong></h3>\n" +
                "<table style=\"border-color: 1px;max-width:1400px\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<th width=\"150px\">主机名称</th>\n" +
                "<th  width=\"80px\">已使用多少容量</th>\n" +
                "<th  width=\"200px\">路径</th>\n" +
                "<th  width=\"100px\">与昨天比较（昨天容量-今天容量）</th>\n" +
                "</tr>");
            if (CollectionUtils.isNotEmpty(data)) {
                for (MysqlServerDisk serverDisk : data) {
                    htmlContent.append("<tr>\n")
                        .append("<td>" + serverDisk.getHostname() + "</td>\n")
                        .append("<td>" + serverDisk.getUsed() + "</td>\n")
                        .append("<td>" + serverDisk.getDisk_path() + "</td>\n")
                        .append("<td>" + serverDisk.getCha() + "</td>\n")
                        .append("</tr>");
                }

                htmlContent.append("</tbody></table>");
            } else {
            	logger.info("no slow table found");
                return;
            }
            htmlContent.append("</div>");
            htmlContent.append("</body></html>");
           
            MailUtils.sendMail( htmlContent.toString());
            //发送邮件
            logger.info("send  slowtable mail done");
        } catch (Exception e) {
        	logger.error("fail to send slowtable mail", e);
        }
	}
	
}
