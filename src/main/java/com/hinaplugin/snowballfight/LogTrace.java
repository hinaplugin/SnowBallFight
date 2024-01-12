package com.hinaplugin.snowballfight;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogTrace {

    public LogTrace(final Exception exception){
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        printWriter.flush();
        SnowBallFight.getPlugin().getLogger().warning(stringWriter.toString());
    }
}
