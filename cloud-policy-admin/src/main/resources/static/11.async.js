(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[11],{"+V7a":function(e,t,a){"use strict";var r=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var l=r(a("p0pE"));a("+BJd");var n=r(a("mr32")),d=r(a("q1tI")),o={labelCol:{span:6},wrapperCol:{span:18}},i=function(){return{name:"\u90e8\u95e8\u7ba1\u7406",path:"permission/department",apiList:{saveInfo:"/dept/save",updateInfo:"/dept/update",deleteInfo:"/dept/del",queryInfo:"/dept/tree"},tableColumns:[{title:"\u90e8\u95e8ID",dataIndex:"key"},{title:"\u90e8\u95e8\u540d\u79f0",dataIndex:"name"},{title:"\u6392\u5e8f",dataIndex:"deptorder"},{title:"\u4fee\u6539\u65f6\u95f4",dataIndex:"updatetime"},{title:"\u72b6\u6001",dataIndex:"status",render:function(e){return 1===e?d.default.createElement(n.default,{color:"#52c41a"},"\u6b63\u5e38"):d.default.createElement(n.default,{color:"#f5222d"},"\u5220\u9664")}}],detailFormItems:[{formType:"CInput",isRequired:!0,key:"name",label:"\u90e8\u95e8\u540d\u79f0",colSpan:24,props:{disabled:!1},formitemprops:(0,l.default)({},o)},{formType:"CSelectDynamicTree",isRequired:!0,key:"parentid",label:"\u4e0a\u7ea7\u90e8\u95e8",colSpan:24,dictionaryKey:"departmentStructure",fetchUrl:"/sys/dept/dic",props:{disabled:!1},formitemprops:(0,l.default)({},o)},{formType:"CSelect",isRequired:!0,initialValue:1,key:"status",label:"\u72b6\u6001",colSpan:24,props:{disabled:!1},formitemprops:(0,l.default)({},o),selectOptions:[{key:1,value:"\u6b63\u5e38"},{key:2,value:"\u5220\u9664"}]},{formType:"CInputNumber",isRequired:!0,key:"deptorder",label:"\u6392\u5e8f",colSpan:24,props:{disabled:!1},formitemprops:(0,l.default)({},o)},{formType:"CTextArea",isRequired:!1,key:"remark",label:"\u5907\u6ce8",colSpan:24,props:{disabled:!1,autosize:{minRows:5,maxRows:10}},formitemprops:(0,l.default)({},o)}]}};t.default=i},It7P:function(e,t,a){"use strict";var r=a("g09b"),l=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("IzEo");var n=r(a("bx4M"));a("14J3");var d=r(a("BMrR"));a("jCWc");var o=r(a("kPKH")),i=r(a("2Taf")),u=r(a("vZ4D")),s=r(a("l4Ni")),p=r(a("ujKo")),f=r(a("MhPg"));a("y8nQ");var c,m,b,y=r(a("Vl3Y")),v=l(a("q1tI")),h=r(a("9N//")),I=(c=y.default.create(),c((b=function(e){function t(){var e,a;(0,i.default)(this,t);for(var r=arguments.length,l=new Array(r),n=0;n<r;n++)l[n]=arguments[n];return a=(0,s.default)(this,(e=(0,p.default)(t)).call.apply(e,[this].concat(l))),a.renderFormItem=function(){var e=a.props,t=e.formItems,r=e.form;return t.map(function(e){var t=(0,h.default)(e,r);return v.default.createElement(o.default,{lg:0===e.colSpan?0:e.colSpan||8,md:0===e.colSpan?0:12,sm:0===e.colSpan?0:24,key:e.key},t)})},a}return(0,f.default)(t,e),(0,u.default)(t,[{key:"render",value:function(){return v.default.createElement(n.default,{bordered:!1,loading:!1},v.default.createElement(y.default,null,v.default.createElement(d.default,{gutter:24},this.renderFormItem())))}}]),t}(v.PureComponent),m=b))||m),g=I;t.default=g},ORRj:function(e,t,a){"use strict";var r=a("g09b"),l=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("2qtc");var n=r(a("kLXV"));a("IzEo");var d=r(a("bx4M"));a("+L6B");var o=r(a("2/Rp")),i=r(a("gWZ8")),u=r(a("p0pE")),s=r(a("2Taf")),p=r(a("vZ4D")),f=r(a("l4Ni")),c=r(a("ujKo")),m=r(a("MhPg"));a("y8nQ");var b,y,v,h,I=r(a("Vl3Y")),g=l(a("q1tI")),k=a("MuoO"),C=r(a("BkRI")),E=r(a("YQYO")),M=r(a("zHco")),V=a("+n12"),w=r(a("jicD")),S=r(a("+V7a")),T=r(a("It7P")),x=(b=(0,k.connect)(function(e){var t=e.user,a=e.loading,r=e.department,l=e.dictionary;return{currentUser:t.currentUser,loading:a.models.department,department:r,dictionary:l}}),y=I.default.create(),b(v=y((h=function(e){function t(e){var a;return(0,s.default)(this,t),a=(0,f.default)(this,(0,c.default)(t).call(this,e)),a.updateFormItems=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},t=(0,C.default)(a.pageConfig.detailFormItems),r=(0,V.formItemAddInitValue)(t,e);a.setState({detailFormItems:r})},a.showModalVisibel=function(e,t){a.updateFormItems(t),a.changeModalVisibel(!0),a.setState({showModalType:e,currentItem:t})},a.hideModalVisibel=function(){a.changeModalVisibel(!1),a.setState({currentItem:{}})},a.changeModalVisibel=function(e){a.props.dispatch({type:"department/modalVisible",payload:{modalVisible:e}})},a.extraTableColumnRender=function(){var e=[{title:"\u64cd\u4f5c",render:function(e,t){if(0!==t.parentid)return g.default.createElement("div",null,g.default.createElement("a",{onClick:function(){a.showModalVisibel("update",t)}},"\u7f16\u8f91"))}}];return e},a.modalOkHandle=function(){a.modalForm.validateFieldsAndScroll(function(e,t){if(!e){var r=a.state.showModalType,l=(0,V.formaterObjectValue)(t);if("create"===r)a.props.dispatch({type:"department/add",payload:l});else if("update"===r){var n=a.state.currentItem.id;a.props.dispatch({type:"department/update",payload:(0,u.default)({id:n},l)})}}})},a.deleteTableRowHandle=function(e){a.props.dispatch({type:"department/remove",payload:{id:e}})},a.renderTable=function(){var e=a.props,t=e.department,r=e.loading,l=a.pageConfig.tableColumns,n=[].concat((0,i.default)(l),(0,i.default)(a.extraTableColumnRender())),d=t.data.list,o={loading:r,dataSource:d,columns:n};return g.default.createElement(E.default,o)},a.pageConfig=(0,S.default)(e.form),a.state={showModalType:"",currentItem:{},detailFormItems:a.pageConfig.detailFormItems},a}return(0,m.default)(t,e),(0,p.default)(t,[{key:"componentDidMount",value:function(){var e=this.props.dispatch;e({type:"department/fetch"})}},{key:"render",value:function(){var e=this,t=this.state.detailFormItems,a=this.props,r=a.department,l=r.modalVisible,i=r.confirmLoading,u=a.dictionary;return g.default.createElement(M.default,null,g.default.createElement(d.default,{bordered:!1},g.default.createElement("div",{className:w.default.tableList},g.default.createElement("div",{className:w.default.tableListForm},g.default.createElement("div",{className:w.default.tableListOperator},g.default.createElement(o.default,{icon:"plus",type:"primary",onClick:function(){return e.showModalVisibel("create",{})}},"\u65b0\u5efa")),this.renderTable()))),g.default.createElement(n.default,{destroyOnClose:!0,visible:l,confirmLoading:i,onCancel:function(){return e.hideModalVisibel()},onOk:function(){e.modalOkHandle()}},g.default.createElement(T.default,{ref:function(t){e.modalForm=t},formItems:t,dictionary:u})))}}]),t}(g.PureComponent),v=h))||v)||v),F=x;t.default=F},jicD:function(e,t,a){e.exports={tableList:"antd-pro-pages-sys-auth-department-route-index-tableList",tableListOperator:"antd-pro-pages-sys-auth-department-route-index-tableListOperator",tableListForm:"antd-pro-pages-sys-auth-department-route-index-tableListForm",submitButtons:"antd-pro-pages-sys-auth-department-route-index-submitButtons"}}}]);