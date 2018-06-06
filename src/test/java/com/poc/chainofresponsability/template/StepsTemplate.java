package com.poc.chainofresponsability.template;

import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class StepsTemplate implements TemplateLoader {
    @Override
    public void load() {

//        Fixture.of(Client.class).addTemplate("valid", new Rule(){{
//            add("id", random(Long.class, range(1L, 200L)));
//            add("name", random("Anderson Parra", "Arthur Hirata"));
//            add("nickname", random("nerd", "geek"));
//            add("email", "${nickname}@gmail.com");
//            add("birthday", instant("18 years ago"));
//            add("address", one(Address.class, "valid"));
//        }});
    }
}
