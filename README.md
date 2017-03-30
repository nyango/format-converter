# FormatConverter(Yaml to Json, Json to Yaml) [![Build Status](https://travis-ci.org/nyango/format-converter.svg?branch=master)](https://travis-ci.org/nyango/format-converter)

Scala wrapper for [MoultingYAML](https://github.com/jcazevedo/moultingyaml) and [Play JSON](https://github.com/playframework/play-json),
in order to convert from yaml to json, and vice versa.

## How to use

When you use it in Scala, add some words as below.

```build.sbt
lazy val yourProject = Project(...

  ).dependsOn(ProjectRef(uri("git://github.com/nyango/format-converter.git#v0.1.1"), "root"))
```
