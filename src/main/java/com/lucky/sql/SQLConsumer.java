package com.lucky.sql;

import java.util.function.Function;

public interface SQLConsumer<I, O> extends Function<I, O> {

    @Override
    default O apply(I t) {
        try {
            return kApply(t);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    O kApply(I t) throws Exception;
}
