(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[31],{NnPv:function(e,a,t){"use strict";var n=t("g09b");Object.defineProperty(a,"__esModule",{value:!0}),a.default=void 0;var r=n(t("p0pE"));t("miYZ");var s=n(t("tsqr")),d=n(t("d6i3")),o=n(t("GIZZ")),c=t("78Ac"),l=t("wjNW"),u=t("UUvt"),i=(0,o.default)(c.tablePageModel,{namespace:"dealermanage",effects:{fetch:d.default.mark(function e(a,t){var n,r,s,o,c,i;return d.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return n=a.payload,r=t.call,s=t.put,e.next=4,r(l.queryPost,n,"/dealer/list");case 4:if(o=e.sent,!o){e.next=12;break}if(c=o.code,i=o.body,200!==c){e.next=10;break}return e.next=10,s({type:"save",payload:{data:i}});case 10:e.next=13;break;case 12:(0,u.showStautsMessageHandle)("error");case 13:case"end":return e.stop()}},e)}),update:d.default.mark(function e(a,t){var n,r,s,o,c,i;return d.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return n=a.payload,r=t.call,s=t.put,e.next=4,s({type:"changgeConfirmLoading",payload:{confirmLoading:!0}});case 4:return e.next=6,r(l.update,n,"/dealer/update");case 6:return o=e.sent,e.next=9,s({type:"changgeConfirmLoading",payload:{confirmLoading:!1}});case 9:if(!o){e.next=19;break}if(c=o.code,i=o.body,200!==c){e.next=16;break}return e.next=14,s({type:"modalVisible",payload:{modalVisible:!1}});case 14:return e.next=16,s({type:"save",payload:{data:i}});case 16:(0,u.showStautsMessageHandle)("general","update",c),e.next=20;break;case 19:(0,u.showStautsMessageHandle)("error");case 20:case"end":return e.stop()}},e)}),add:d.default.mark(function e(a,t){var n,r,s,o,c,i;return d.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return n=a.payload,r=t.call,s=t.put,e.next=4,s({type:"changgeConfirmLoading",payload:{confirmLoading:!0}});case 4:return e.next=6,r(l.create,n,"/dealer/add");case 6:return o=e.sent,e.next=9,s({type:"changgeConfirmLoading",payload:{confirmLoading:!1}});case 9:if(!o){e.next=19;break}if(c=o.code,i=o.body,200!==c){e.next=16;break}return e.next=14,s({type:"modalVisible",payload:{modalVisible:!1}});case 14:return e.next=16,s({type:"save",payload:{data:i}});case 16:(0,u.showStautsMessageHandle)("general","add",c),e.next=20;break;case 19:(0,u.showStautsMessageHandle)("error");case 20:case"end":return e.stop()}},e)}),resetPassword:d.default.mark(function e(a,t){var n,r,o,c,i,p;return d.default.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return n=a.payload,r=t.call,o=t.put,e.next=4,r(l.remove,n,"/dealer/resetpassword");case 4:if(c=e.sent,!c){e.next=13;break}if(i=c.code,p=c.body,200!==i){e.next=11;break}return e.next=10,o({type:"save",payload:{data:p}});case 10:s.default.success("\u5bc6\u7801\u91cd\u7f6e\u6210\u529f");case 11:e.next=14;break;case 13:(0,u.showStautsMessageHandle)("error");case 14:case"end":return e.stop()}},e)})},reducers:{save:function(e,a){return(0,r.default)({},e,{data:(0,r.default)({},a.payload.data,{list:a.payload.data.list.map(function(e){return(0,r.default)({},e,{region:[e.province,e.city,e.district]})})})})}}});a.default=i}}]);