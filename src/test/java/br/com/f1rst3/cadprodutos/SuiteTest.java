package br.com.f1rst3.cadprodutos;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
//@SelectPackages({"br.com.ada3.cadprodutos"})
@SelectClasses({CadProdutosRestControllerTest.class, CadProdutosServiceTest.class} )
public class SuiteTest {
}
