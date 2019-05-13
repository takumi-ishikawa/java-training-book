package com.example.dao;

import com.example.dao.converter.*;
import com.example.model.AliasName;
import org.seasar.doma.DomainConverters;

@DomainConverters({
    CreatedAtConverter.class,
    UserIdConverter.class,
    UserNameConverter.class,
    UserTokenConverter.class,
    AliasIdConverter.class,
    AliasValueConverter.class,
    AliasNameConverter.class
})
public class DomainConvertersProvider {
}
