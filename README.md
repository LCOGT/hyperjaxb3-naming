# Description

Restore 0.5.x naming convention of hyperjaxb3 maven plugin to prevent DB changes.

When hyperjaxb3 plugin migrated from 0.5 to 0.6 the naming convention changed to convert camel
case attributes to underscore MYSQl table or column names,e.g. defaultString creates a column
DEFAULT_STRING cf DEFAULTSTRING previously.

This override to the plugin restores the old behavior.

NOTE: the simplistic approach [https://github.com/highsource/hyperjaxb3-support/issues/7] 
use by others to fix this problem (remove underscores) does not work
because underscores are still valid to separate embedded types, e.g. 

<xs:element name="samplePeriod" type="tns:value"/>

should generate columns 

| SAMPLEPERIOD_UNITS             | varchar(255) | YES  |     | NULL    |       |
| SAMPLEPERIOD_VALUE             | double       | YES  |     | NULL    |       |

also

<xs:element name="value" type="tns:valueDescriptionType"/>

should generate

| VALUE__DATADESCRIPTIONTYPE_I_0 | bigint(20)   | YES  | MUL | NULL    |       |