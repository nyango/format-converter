package io.github.nyango.formatconverter

import net.jcazevedo.moultingyaml._
import org.scalatest.FlatSpec

class FormatConverterSpec extends FlatSpec {

  private val yaml1 =
    """invoice: 34843
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
      |total: 4443.52""".stripMargin + "\n"

  private val yaml1_with_date =
    """invoice: 34843
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
      |total: 4443.52""".stripMargin + "\n"

  private val yaml1_quoted =
    """"ship-to":
      |  "family": "Dumars"
      |  "address":
      |    "lines": "458 Walkman Dr.\nSuite #292\n"
      |    "city": "Royal Oak"
      |    "postal": !!int "48046"
      |    "state": "MI"
      |  "given": "Chris"
      |"tax": !!float "251.42"
      |"total": !!float "4443.52"
      |"invoice": !!int "34843"
      |"bill-to":
      |  "family": "Dumars"
      |  "address":
      |    "lines": "458 Walkman Dr.\nSuite #292\n"
      |    "city": "Royal Oak"
      |    "postal": !!int "48046"
      |    "state": "MI"
      |  "given": "Chris"
      |"product":
      |- "description": "Basketball"
      |  "quantity": !!int "4"
      |  "price": !!float "450.0"
      |  "sku": "BL394D"
      |- "description": "Super Hoop"
      |  "quantity": !!int "1"
      |  "price": !!float "2392.0"
      |  "sku": "BL4438H"""".stripMargin + "\n"

  private val yaml1_flowing =
    """{ship-to: {family: Dumars, address: {lines: "458 Walkman Dr.\nSuite #292\n", city: Royal Oak,
      |      postal: 48046, state: MI}, given: Chris}, tax: 251.42, total: 4443.52, invoice: 34843,
      |  bill-to: {family: Dumars, address: {lines: "458 Walkman Dr.\nSuite #292\n", city: Royal Oak,
      |      postal: 48046, state: MI}, given: Chris}, product: [{description: Basketball,
      |      quantity: 4, price: 450.0, sku: BL394D}, {description: Super Hoop, quantity: 1,
      |      price: 2392.0, sku: BL4438H}]}""".stripMargin + "\n"

  private val yaml1_win_linebreak =
    """ship-to:
      |  family: Dumars
      |  address:
      |    lines: |
      |      458 Walkman Dr.
      |      Suite #292
      |    city: Royal Oak
      |    postal: 48046
      |    state: MI
      |  given: Chris
      |tax: 251.42
      |total: 4443.52
      |invoice: 34843
      |bill-to:
      |  family: Dumars
      |  address:
      |    lines: |
      |      458 Walkman Dr.
      |      Suite #292
      |    city: Royal Oak
      |    postal: 48046
      |    state: MI
      |  given: Chris
      |product:
      |- description: Basketball
      |  quantity: 4
      |  price: 450.0
      |  sku: BL394D
      |- description: Super Hoop
      |  quantity: 1
      |  price: 2392.0
      |  sku: BL4438H""".stripMargin.replaceAll("\n", "\r\n") + "\r\n"

  private val yamlValue1         = yaml1.parseYaml
  private val yamlValue1WithDate = yaml1.parseYaml

  private val jsValue1         = FormatConverter.yamlStrToJsValue(yaml1)
  private val jsValue1WithDate = FormatConverter.yamlStrToJsValue(yaml1_with_date)

  "yamlToJson" should "converts correctly, except date" in {
    assert(FormatConverter.jsValueToYamlStr(jsValue1).parseYaml == yamlValue1)
    assert(FormatConverter.jsValueToYamlStr(jsValue1WithDate).parseYaml !== yamlValue1WithDate)
  }

  "jsValueToYamlStr" should "be set a scalarStyle" in {
    assert(FormatConverter.jsValueToYamlStr(jsValue1, scalarStyle = DoubleQuoted) == yaml1_quoted)
    assert(yamlValue1 == yaml1_quoted.parseYaml)
  }

  it should "be set a flowStyle" in {
    assert(FormatConverter.jsValueToYamlStr(jsValue1, flowStyle = Flow) == yaml1_flowing)
    assert(yamlValue1 == yaml1_flowing.parseYaml)
  }

  it should "be set a lineBreak" in {
    assert(FormatConverter.jsValueToYamlStr(jsValue1, lineBreak = Win) == yaml1_win_linebreak)
    assert(yamlValue1 == yaml1_win_linebreak.parseYaml)
  }
}
