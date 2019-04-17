package com.example.dao;

import com.example.dao.converter.CreatedAtConverter;
import com.example.dao.converter.UserIdConverter;
import com.example.dao.converter.UserNameConverter;
import com.example.dao.converter.UserTokenConverter;
import org.seasar.doma.DomainConverters;

@DomainConverters({
    CreatedAtConverter.class,
    UserIdConverter.class,
    UserNameConverter.class,
    UserTokenConverter.class
})
public class DomainConvertersProvider {
}
