package com.bookstore.runners;

import com.bookstore.specs.Login;
import com.bookstore.specs.RegistUser;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ Login.class, RegistUser.class })
@SelectPackages("src/test/java/com/bookstore/specs")
@IncludeTags("regression")
public class RunningTests {}