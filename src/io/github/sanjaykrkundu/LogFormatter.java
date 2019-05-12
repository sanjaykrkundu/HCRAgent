package io.github.sanjaykrkundu;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder(1000);
		builder.append(HCRAgent.dateFormatter.format(new Date(record.getMillis()))).append(" - ");
		builder.append("[Agent] - ").append(formatMessage(record)).append("\n");
		return builder.toString();
	}

	public String getHead(Handler h) {
		return super.getHead(h);
	}

	public String getTail(Handler h) {
		return super.getTail(h);
	}
}