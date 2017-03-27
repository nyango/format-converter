package net.nyango.formatconverter

import net.jcazevedo.moultingyaml._
import play.api.libs.json._

object FormatConverter {
  def yamlStrToJsValue(yamlStr: String): JsValue = {
    yamlValueToJsValue(yamlStr.toYaml)
  }

  def jsValueToYamlStr(json: JsValue): String = {
    jsValueToYamlValue(json).prettyPrint
  }

  def jsValueToYamlValue(json: JsValue): YamlValue = json match {
    case x: JsObject =>
      YamlObject(x.fields.map(tpl =>
        (YamlString(tpl._1): YamlValue) -> jsValueToYamlValue(tpl._2)).toMap)
    case x: JsArray =>
      YamlArray(x.value.map(jsValueToYamlValue).toVector)
    case n: JsNumber =>
      YamlNumber(n.value)
    case s: JsString =>
      YamlString(s.value)
    case b: JsBoolean =>
      YamlBoolean(b.value)
    case _ => YamlNull
  }

  def yamlValueToJsValue(yaml: YamlValue): JsValue = yaml match {
    case x: YamlObject =>
      JsObject(x.fields.map(tpl => tpl._1.toString -> yamlValueToJsValue(tpl._2)))
    case x: YamlArray =>
      JsArray(x.elements.map(yamlValueToJsValue))
    case n: YamlNumber =>
      JsNumber(n.value)
    case s: YamlString =>
      JsString(s.value)
    case b: YamlBoolean =>
      JsBoolean(b.boolean)
    case _ => JsNull
  }
}
