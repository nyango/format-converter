package io.github.nyango.formatconverter

import net.jcazevedo.moultingyaml._
import play.api.libs.json._

object FormatConverter {
  def yamlStrToJsValue(yamlStr: String): JsValue = {
    yamlValueToJsValue(yamlStr.parseYaml)
  }

  def jsValueToYamlStr(
      json: JsValue,
      flowStyle: FlowStyle = FlowStyle.DEFAULT,
      scalarStyle: ScalarStyle = ScalarStyle.DEFAULT,
      lineBreak: LineBreak = LineBreak.DEFAULT
  ): String = {
    jsValueToYamlValue(json).print(
      flowStyle = flowStyle,
      scalarStyle = scalarStyle,
      lineBreak = lineBreak
    )
  }

  def jsValueToYamlValue(json: JsValue): YamlValue = json match {
    case x: JsObject  =>
      YamlObject(x.fields.map(tpl =>
        (YamlString(tpl._1): YamlValue) -> jsValueToYamlValue(tpl._2)).toMap)
    case x: JsArray   =>
      YamlArray(x.value.map(jsValueToYamlValue).toVector)
    case n: JsNumber  =>
      YamlNumber(n.value)
    case s: JsString  =>
      YamlString(s.value)
    case b: JsBoolean =>
      YamlBoolean(b.value)
    case _            => YamlNull
  }

  def yamlValueToJsValue(yaml: YamlValue): JsValue = yaml match {
    case x: YamlObject  =>
      JsObject(x.fields.collect {
        case (YamlString(key), v: YamlValue) => key -> yamlValueToJsValue(v)
      })
    case x: YamlArray   =>
      JsArray(x.elements.map(yamlValueToJsValue))
    case n: YamlNumber  =>
      JsNumber(n.value)
    case s: YamlString  =>
      JsString(s.value)
    case b: YamlBoolean =>
      JsBoolean(b.boolean)
    case d: YamlDate    => // There is not any "JsDate" in play-json
      JsString(d.prettyPrint)
    case _              => JsNull
  }
}
