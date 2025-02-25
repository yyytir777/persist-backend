package yyytir777.persist.domain.log;

import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.log.entity.Log;

public class LogTestConverter {
    public static Log createLogInTest(Long logId, Category category) {
        return new Log(logId, category);
    }

    public static Log createLogInTest(Category category) {
        return new Log(null, category);
    }
}
