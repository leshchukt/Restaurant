package model.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(DateTimeTag.class);

    private LocalDateTime dateTime;

    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy H:mm:ss"));
        try {
            pageContext.getOut().write(formattedDateTime);
        } catch (IOException e) {
            LOGGER.error("Error during the output of the formatted dateTime: ", e);
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }
}
