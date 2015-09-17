package com.panipuri.presentation.viewbuilding;

import flexjson.transformer.AbstractTransformer;

public class ExcludeTransformer extends AbstractTransformer {
    
    @Override
    public Boolean isInline() {
        return true;
    }

    public void transform(Object object) {
    }

}
