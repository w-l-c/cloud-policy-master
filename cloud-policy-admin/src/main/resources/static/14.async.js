(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[14],{L1PO:function(e,t,a){"use strict";var r=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var s=r(a("p0pE")),o={labelCol:{span:6},wrapperCol:{span:18}},n=function(){return{name:"\u7cfb\u7edf\u65e5\u5fd7",path:"systemlog",tableColumns:[{title:"Id",dataIndex:"id"},{title:"\u7528\u6237\u540d",dataIndex:"username"},{title:"\u7528\u6237\u64cd\u4f5c",dataIndex:"logtype"},{title:"IP\u5730\u5740",dataIndex:"sourceip"},{title:"\u521b\u5efa\u65f6\u95f4",dataIndex:"createtime"}],searchForms:[{formType:"CInput",isRequired:!1,key:"username",label:"\u7528\u6237\u540d",props:{disabled:!1},formitemprops:(0,s.default)({},o)},{formType:"CInput",isRequired:!1,key:"sourceip",label:"IP\u5730\u5740",props:{disabled:!1},formitemprops:(0,s.default)({},o)}]}};t.default=n},SJ93:function(e,t,a){"use strict";var r=a("g09b"),s=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("IzEo");var o=r(a("bx4M")),n=r(a("gWZ8")),u=r(a("p0pE")),l=r(a("2Taf")),i=r(a("vZ4D")),d=r(a("l4Ni")),p=r(a("ujKo")),f=r(a("MhPg"));a("y8nQ");var m,c,g,y,b=r(a("Vl3Y")),h=s(a("q1tI")),v=a("MuoO"),q=a("+n12"),O=r(a("JpOw")),I=r(a("n+/z")),x=r(a("zHco")),F=r(a("L1PO")),P=r(a("ex0p")),S=(m=(0,v.connect)(function(e){var t=e.user,a=e.systemlog,r=e.loading;return{currentUser:t.currentUser,systemlog:a,loading:r.models.systemlog}}),c=b.default.create(),m(g=c((y=function(e){function t(e){var a;return(0,l.default)(this,t),a=(0,d.default)(this,(0,p.default)(t).call(this,e)),a.queryParamsFormater=function(e,t){var r=a.props.systemlog.data.pagination;delete r.total;var s={form:{},query:{},pagination:{current:1,pageSize:10}};switch(t){case 1:Object.assign(s,{query:(0,u.default)({},e)});break;case 2:Object.assign(s,{query:(0,u.default)({},a.state.queryValues),form:(0,u.default)({},e),pagination:r});break;case 3:Object.assign(s,{form:(0,u.default)({},e)});break;case 4:Object.assign(s,{query:(0,u.default)({},a.state.queryValues),pagination:{current:e.page,pageSize:e.pageSize}});break;default:Object.assign(s,{})}return s},a.renderSearchForm=function(){var e=a.props,t=e.form,r=e.dispatch,s=a.pageConfig.searchForms,o={form:t,formInfo:{layout:"inline",formItems:s},handleSearchSubmit:function(e){var t=Object.assign({},e,{}),s=(0,q.formaterObjectValue)(t);a.setState({queryValues:s}),r({type:"systemlog/fetch",payload:a.queryParamsFormater(s,1)})},handleFormReset:function(){a.setState({queryValues:{}}),r({type:"systemlog/fetch",payload:a.queryParamsFormater()})}};return h.default.createElement(O.default,o)},a.renderTable=function(){var e=a.props,t=e.systemlog,r=e.loading,s=a.pageConfig.tableColumns,o=t.data,l=o.list,i=o.pagination,d=(0,n.default)(s),p={loading:r,dataSource:l,columns:d,pagination:Object.assign(i,{pageSize:10}),handleTableChange:function(e){var t=e.current,r=a.props.dispatch,s=a.state.formValues,o=(0,u.default)({page:t,pageSize:10},s);r({type:"systemlog/fetch",payload:a.queryParamsFormater(o,4)})},bordered:!1};return h.default.createElement(I.default,p)},a.pageConfig=(0,F.default)(e.form),a.state={formValues:{},queryValues:{}},a}return(0,f.default)(t,e),(0,i.default)(t,[{key:"componentDidMount",value:function(){var e=this.props.dispatch;e({type:"systemlog/fetch",payload:this.queryParamsFormater()})}},{key:"render",value:function(){return h.default.createElement(x.default,null,h.default.createElement(o.default,{bordered:!1},h.default.createElement("div",{className:P.default.tableList},h.default.createElement("div",{className:P.default.tableListForm},this.renderSearchForm(),this.renderTable()))))}}]),t}(h.PureComponent),g=y))||g)||g),j=S;t.default=j},ex0p:function(e,t,a){e.exports={tableList:"antd-pro-pages-sys-auth-system-log-route-index-tableList",tableListOperator:"antd-pro-pages-sys-auth-system-log-route-index-tableListOperator",tableListForm:"antd-pro-pages-sys-auth-system-log-route-index-tableListForm",submitButtons:"antd-pro-pages-sys-auth-system-log-route-index-submitButtons"}}}]);