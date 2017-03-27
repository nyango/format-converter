package io.github.nyango.formatconverter

import net.jcazevedo.moultingyaml._
import org.scalatest.FlatSpec
import play.api.libs.json.JsObject

class FormatConverterSpec extends FlatSpec {

  private val yaml1 =
    """
      |invoice: 34843
      |date   : 2001-01-23
      |bill-to: &id001
      |    given  : Chris
      |    family : Dumars
      |    address:
      |        lines: |
      |            458 Walkman Dr.
      |            Suite #292
      |        city    : Royal Oak
      |        state   : MI
      |        postal  : 48046
      |ship-to: *id001
      |product:
      |    - sku         : BL394D
      |      quantity    : 4
      |      description : Basketball
      |      price       : 450.00
      |    - sku         : BL4438H
      |      quantity    : 1
      |      description : Super Hoop
      |      price       : 2392.00
      |tax  : 251.42
      |total: 4443.52
    """.stripMargin


  private val yaml1_without_date =
    """
      |invoice: 34843
      |bill-to: &id001
      |    given  : Chris
      |    family : Dumars
      |    address:
      |        lines: |
      |            458 Walkman Dr.
      |            Suite #292
      |        city    : Royal Oak
      |        state   : MI
      |        postal  : 48046
      |ship-to: *id001
      |product:
      |    - sku         : BL394D
      |      quantity    : 4
      |      description : Basketball
      |      price       : 450.00
      |    - sku         : BL4438H
      |      quantity    : 1
      |      description : Super Hoop
      |      price       : 2392.00
      |tax  : 251.42
      |total: 4443.52
    """.stripMargin

  "yamlToJson" should "converts correctly, except date" in {
    val jsValue = FormatConverter.yamlStrToJsValue(yaml1)
    assert(FormatConverter.jsValueToYamlStr(jsValue.as[JsObject] - "date").parseYaml == yaml1_without_date.parseYaml)
  }
}
