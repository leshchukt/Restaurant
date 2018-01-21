package model.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(DateTimeTag.class);

    private LocalDate date;

    public void setDate(LocalDate date){
        this.date = date;
    }

    @Override
    public int doStartTag() throws JspException {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        try {
            pageContext.getOut().write(formattedDate);
        } catch (IOException e) {
            LOGGER.error("Error during the output of the formatted date: ", e);
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }
}
