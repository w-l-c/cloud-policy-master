(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[5],{"2Ml+":function(e,t,a){"use strict";var r=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var d=r(a("p0pE"));a("Awhp");var l=r(a("KrTs")),i=r(a("q1tI")),n={labelCol:{span:6},wrapperCol:{span:18}},o=function(){return{name:"\u8ba2\u5355\u5217\u8868",path:"orderlistmanage",tableColumns:[{title:"\u8ba2\u5355\u7f16\u53f7",dataIndex:"orderSeq"},{title:"\u8f66\u8f86\u6b3e\u578b",dataIndex:"modelName"},{title:"\u8f66\u724c\u53f7",dataIndex:"carNumber"},{title:"\u7ade\u4ef7\u6210\u4ea4\u91d1\u989d\uff08\u4e07\u5143\uff09",dataIndex:"bidPrice"},{title:"\u5f00\u59cb\u65f6\u95f4",dataIndex:"startTime"},{title:"\u7ed3\u675f\u65f6\u95f4",dataIndex:"endTime"},{title:"\u6210\u4ea4\u65f6\u95f4",dataIndex:"completeTime"},{title:"\u552e\u5356\u95e8\u5e97",dataIndex:"dealerName"},{title:"\u7ade\u4ef7\u6210\u4ea4\u95e8\u5e97",dataIndex:"bidderName"},{title:"\u8ba2\u5355\u72b6\u6001",dataIndex:"status",width:110,fixed:"right",render:function(e){return 1===e?i.default.createElement(l.default,{status:"default",text:"\u5f85\u7ade\u62cd"}):2===e?i.default.createElement(l.default,{status:"processing",text:"\u7ade\u4ef7\u4e2d"}):3===e?i.default.createElement(l.default,{status:"processing",text:"\u5ba1\u6838\u4e2d"}):4===e?i.default.createElement(l.default,{status:"success",text:"\u7ade\u4ef7\u5b8c\u6210"}):5===e?i.default.createElement(l.default,{status:"error",text:"\u8ba2\u5355\u5173\u95ed"}):null}}],bidderTableColumns:[{title:"\u7ade\u4ef7\u65b9",dataIndex:"store"},{title:"\u7ade\u4ef7\u65b9\u59d3\u540d",dataIndex:"name"},{title:"\u8054\u7cfb\u65b9\u5f0f",dataIndex:"mobile"},{title:"\u5730\u533a",dataIndex:"region"},{title:"\u7ade\u4ef7\u91d1\u989d",dataIndex:"bidPrice"},{title:"\u7ade\u4ef7\u65f6\u95f4",dataIndex:"startBidderTime"}],searchForms:[{formType:"CInput",isRequired:!1,key:"orderSeq",label:"\u8ba2\u5355\u7f16\u53f7",props:{},formitemprops:(0,d.default)({},n)},{formType:"CInput",isRequired:!1,key:"modelName",label:"\u8f66\u8f86\u6b3e\u578b",props:{},formitemprops:(0,d.default)({},n)},{formType:"CInput",isRequired:!1,key:"carNumber",label:"\u8f66\u724c\u53f7",props:{},formitemprops:(0,d.default)({},n)},{formType:"CRangePicker",isRequired:!1,key:"completeTime",label:"\u6210\u4ea4\u65f6\u95f4",props:{},formitemprops:(0,d.default)({},n)},{formType:"CSelect",isRequired:!1,initialValue:"",key:"status",label:"\u8ba2\u5355\u72b6\u6001",props:{},formitemprops:(0,d.default)({},n),selectOptions:[{key:"",value:"\u5168\u90e8"},{key:1,value:"\u5f85\u7ade\u62cd"},{key:2,value:"\u7ade\u4ef7\u4e2d"},{key:3,value:"\u5ba1\u6838\u4e2d"},{key:4,value:"\u7ade\u4ef7\u5b8c\u6210"},{key:5,value:"\u8ba2\u5355\u5173\u95ed"}]}],addBidderFormItems:[{formType:"CInput",isRequired:!1,key:"orderId",label:"id",colSpan:0,props:{disabled:!1}},{formType:"CSelectDynamic",isRequired:!0,key:"bidderId",label:"\u7ade\u4ef7\u65b9",colSpan:24,dictionaryKey:"bidderDic",fetchUrl:"/bidder/dic",props:{disabled:!1}},{formType:"CInputNumber",isRequired:!0,key:"bidPrice",label:"\u7ade\u4ef7\u91d1\u989d\uff08\u5143\uff09",colSpan:24,props:{disabled:!1}}],updateBidderFormItems:[{formType:"CInput",isRequired:!1,key:"orderId",label:"id",colSpan:0,props:{disabled:!1}},{formType:"CInput",isRequired:!1,key:"id",label:"id",colSpan:0,props:{disabled:!1}},{formType:"CInput",isRequired:!1,key:"bidderId",label:"bidderId",colSpan:0,props:{disabled:!1}},{formType:"CInput",isRequired:!0,key:"store",label:"\u7ade\u4ef7\u65b9",colSpan:24,props:{disabled:!0}},{formType:"CInputNumber",isRequired:!0,key:"bidPrice",label:"\u7ade\u4ef7\u91d1\u989d\uff08\u5143\uff09",colSpan:24,props:{disabled:!1}}],ensureBidderFormItems:[{formType:"CInput",isRequired:!1,key:"orderId",label:"id",colSpan:0,props:{disabled:!1}},{formType:"CInput",isRequired:!1,key:"bidderId",label:"bidderId",colSpan:0,props:{disabled:!1}},{formType:"CInput",isRequired:!0,key:"store",label:"\u7ade\u4ef7\u65b9",colSpan:12,props:{disabled:!0}},{formType:"CInput",isRequired:!0,key:"name",label:"\u7ade\u4ef7\u65b9\u59d3\u540d",colSpan:12,props:{disabled:!0}},{formType:"CInput",isRequired:!0,key:"mobile",label:"\u8054\u7cfb\u65b9\u5f0f",colSpan:12,props:{disabled:!0}},{formType:"CInputNumber",isRequired:!0,key:"bidPrice",label:"\u7ade\u4ef7\u91d1\u989d\uff08\u5143\uff09",colSpan:12,props:{disabled:!0}}]}};t.default=o},Nx2e:function(e,t,a){"use strict";var r=a("g09b"),d=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("2qtc");var l=r(a("kLXV"));a("IzEo");var i=r(a("bx4M"));a("+L6B");var n=r(a("2/Rp")),o=r(a("gWZ8"));a("P2fV");var u=r(a("NJEC")),s=r(a("p0pE")),c=r(a("2Taf")),m=r(a("vZ4D")),f=r(a("l4Ni")),p=r(a("ujKo")),b=r(a("rlhR")),g=r(a("MhPg"));a("y8nQ");var y,v,h,I,E=r(a("Vl3Y")),k=d(a("q1tI")),q=a("MuoO"),C=r(a("BkRI")),T=(r(a("BGR+")),r(a("n+/z"))),P=(r(a("ug3m")),a("+n12")),x=r(a("Px8D")),S=r(a("2Ml+")),w=r(a("TEpi")),B=(y=(0,q.connect)(function(e){var t=e.orderlistmanage,a=e.loading,r=a.effects["orderlistmanage/fetchBidderList"],d=a.effects["orderlistmanage/addBidder"],l=a.effects["orderlistmanage/updateBidderPrice"],i=a.effects["orderlistmanage/pushBidderPrice"];return{orderlistmanage:t,loading:r||d||l||i}}),v=E.default.create(),y(h=v((I=function(e){function t(e){var a;(0,c.default)(this,t),a=(0,f.default)(this,(0,p.default)(t).call(this,e)),a.pageConfig=(0,S.default)((0,b.default)(a)),a.state={currentId:-1,showModalType:"",formValues:{},queryValues:{},detailFormItems:[]},a.queryParamsFormater=function(e,t){var r=a.props.orderlistmanage,d=r.bidderList.pagination,l=r.orderId;delete d.total;var i={form:{},query:{orderId:l},pagination:{current:1,pageSize:10}};switch(t){case 1:Object.assign(i,{query:(0,s.default)({},e)});break;case 2:Object.assign(i,{query:(0,s.default)({},a.state.queryValues),form:(0,s.default)({},e),pagination:d});break;case 3:Object.assign(i,{form:(0,s.default)({},e)});break;case 4:Object.assign(i,{query:(0,s.default)({},a.state.queryValues),pagination:{current:e.page,pageSize:e.pageSize}});break;default:Object.assign(i,{})}return i},a.updateFormItems=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"addBidder",r=(0,C.default)(a.pageConfig["".concat(t,"FormItems")]),d=(0,P.formItemAddInitValue)(r,e);a.setState({detailFormItems:d})},a.changeModalVisibel=function(e){a.props.dispatch({type:"orderlistmanage/modalVisible",payload:{modalVisible:e}})},a.showModalVisibel=function(e,t){"addBidder"===e?a.modalTitle="\u6dfb\u52a0\u7ade\u62cd\u65b9":"updateBidder"===e?a.modalTitle="\u4fee\u6539\u7ade\u4ef7":"ensureBidder"===e&&(a.modalTitle="\u786e\u8ba4\u62cd\u5356"),a.updateFormItems(t,e),a.changeModalVisibel(!0),a.setState({showModalType:e})},a.hideModalVisibel=function(){a.changeModalVisibel(!1)},a.pushBidderPriceHandle=function(e){a.props.dispatch({type:"orderlistmanage/pushBidderPrice",payload:a.queryParamsFormater((0,s.default)({},e),3)})},a.ensureBidderHandle=function(){var e=a.props.orderId,t=a.props.orderlistmanage.bidderList,r=t.winnerId,d=t.list,l=void 0===d?[]:d,i=l.find(function(e){return e.id===r}),n=(i.id,i.bidderId);a.showModalVisibel("ensureBidder",(0,s.default)({},i,{bidderId:n,orderId:e}))},a.extraTableColumnRender=function(){var e=a.props,t=e.orderId,r=e.orderlistmanage,d=void 0===r?{}:r,l=d.bidderList,i=void 0===l?{}:l,n=i.status;if(4===n||5===n)return[];var o=[{title:"\u64cd\u4f5c",width:130,fixed:"right",render:function(e){var r=e.id,d=e.store,l=e.bidderId;return k.default.createElement("div",null,(2===n||3===n)&&k.default.createElement("a",{onClick:function(){a.showModalVisibel("updateBidder",(0,s.default)({},e,{bidderId:l,id:r,orderId:t}))}},"\u4fee\u6539"),"\xa0\xa0",3===n&&k.default.createElement(u.default,{title:"\u662f\u5426\u786e\u5b9a\u63a8\u9001".concat(d,"\u4e3a\u4e2d\u6807\u65b9?"),onConfirm:function(){a.pushBidderPriceHandle({orderId:t,bidderId:l,id:r})}},k.default.createElement("a",null,"\u63a8\u9001\u4e2d\u6807\u4ef7")))}}];return o},a.modalOkHandle=function(){a.modalForm.validateFieldsAndScroll(function(e,t){if(!e){var r=a.state.showModalType,d=(0,P.formaterObjectValue)(t);"addBidder"===r?a.props.dispatch({type:"orderlistmanage/addBidder",payload:a.queryParamsFormater(d,3)}):"updateBidder"===r?a.props.dispatch({type:"orderlistmanage/updateBidderPrice",payload:a.queryParamsFormater(d,2)}):"ensureBidder"===r&&a.props.dispatch({type:"orderlistmanage/eusureBidderPrice",payload:a.queryParamsFormater(d,3)})}})},a.renderTable=function(){var e=a.props,t=e.orderlistmanage,r=e.loading,d=a.pageConfig.bidderTableColumns,l=t.bidderList,i=l.list,n=void 0===i?[]:i,u=l.pagination,c=void 0===u?{}:u,m=l.winnerId,f=void 0===m?-1:m,p=[].concat((0,o.default)(d),(0,o.default)(a.extraTableColumnRender())),b={loading:r,dataSource:n,columns:p,pagination:Object.assign(c,{pageSize:10}),handleTableChange:function(e){var t=e.current,r=a.props.dispatch,d=a.state.formValues,l=(0,s.default)({page:t,pageSize:10},d);r({type:"orderlistmanage/fetchBidderList",payload:a.queryParamsFormater(l,4)})},bordered:!0,otherProps:{scroll:{x:800},size:"small",rowClassName:function(e){return e.id===f?w.default.highlight:""}}};return k.default.createElement(T.default,b)};e.orderId;return a}return(0,g.default)(t,e),(0,m.default)(t,[{key:"componentDidMount",value:function(){var e=this.props,t=e.dispatch,a=e.orderId;t({type:"orderlistmanage/fetchBidderList",payload:this.queryParamsFormater({orderId:a},1)})}},{key:"render",value:function(){var e=this,t=this.state.detailFormItems,a=this.props,r=a.orderlistmanage,d=r.modalVisible,o=r.confirmLoading,u=(r.bidderList.winnerId,a.orderId),s=a.currentItem.status;return k.default.createElement(k.Fragment,null,k.default.createElement(i.default,null,k.default.createElement("div",{className:w.default.tableList},(4!==s||5!==s)&&k.default.createElement("div",{className:w.default.tableListOperator},(2===s||3===s)&&k.default.createElement(n.default,{icon:"plus",type:"primary",onClick:function(){return e.showModalVisibel("addBidder",{orderId:u})}},"\u6dfb\u52a0\u7ade\u4ef7\u65b9"),3===s&&k.default.createElement(n.default,{onClick:this.ensureBidderHandle},"\u786e\u8ba4\u62cd\u5356")),k.default.createElement("div",{className:w.default.tableListForm},this.renderTable()))),k.default.createElement(l.default,{title:this.modalTitle,width:600,maskClosable:!1,destroyOnClose:!0,visible:d,confirmLoading:o,onCancel:function(){return e.hideModalVisibel()},onOk:function(){e.modalOkHandle()}},k.default.createElement(x.default,{ref:function(t){e.modalForm=t},formItems:t})))}}]),t}(k.PureComponent),h=I))||h)||h),F=B;t.default=F},Px8D:function(e,t,a){"use strict";var r=a("g09b"),d=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("IzEo");var l=r(a("bx4M"));a("14J3");var i=r(a("BMrR"));a("jCWc");var n=r(a("kPKH")),o=r(a("2Taf")),u=r(a("vZ4D")),s=r(a("l4Ni")),c=r(a("ujKo")),m=r(a("MhPg"));a("y8nQ");var f,p,b,g=r(a("Vl3Y")),y=d(a("q1tI")),v=r(a("9N//")),h=(f=g.default.create(),f((b=function(e){function t(){var e,a;(0,o.default)(this,t);for(var r=arguments.length,d=new Array(r),l=0;l<r;l++)d[l]=arguments[l];return a=(0,s.default)(this,(e=(0,c.default)(t)).call.apply(e,[this].concat(d))),a.renderFormItem=function(){var e=a.props,t=e.formItems,r=e.form,d=t.map(function(e){var t=(0,v.default)(e,r);return y.default.createElement(n.default,{lg:0===e.colSpan?0:e.colSpan||8,md:0===e.colSpan?0:12,sm:0===e.colSpan?0:24,key:e.key},t)});return d},a}return(0,m.default)(t,e),(0,u.default)(t,[{key:"render",value:function(){return y.default.createElement(l.default,{bordered:!1,loading:!1},y.default.createElement(g.default,null,y.default.createElement(i.default,{gutter:24},this.renderFormItem())))}}]),t}(y.PureComponent),p=b))||p),I=h;t.default=I},TEpi:function(e,t,a){e.exports={tableList:"antd-pro-pages-order-manage-order-list-manage-route-index-tableList",tableListOperator:"antd-pro-pages-order-manage-order-list-manage-route-index-tableListOperator",tableListForm:"antd-pro-pages-order-manage-order-list-manage-route-index-tableListForm",submitButtons:"antd-pro-pages-order-manage-order-list-manage-route-index-submitButtons",highlight:"antd-pro-pages-order-manage-order-list-manage-route-index-highlight"}},Z6j4:function(e,t,a){"use strict";var r=a("g09b"),d=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("IzEo");var l,i,n=r(a("bx4M")),o=r(a("2Taf")),u=r(a("vZ4D")),s=r(a("l4Ni")),c=r(a("ujKo")),m=r(a("MhPg")),f=d(a("q1tI")),p=a("MuoO"),b=r(a("+kNj")),g=b.default.Description,y=(l=(0,p.connect)(function(e){var t=e.tabpages,a=t.orderInfo,r=t.loading;return{orderInfo:a,loading:r}}),l(i=function(e){function t(){return(0,o.default)(this,t),(0,s.default)(this,(0,c.default)(t).apply(this,arguments))}return(0,m.default)(t,e),(0,u.default)(t,[{key:"componentDidMount",value:function(){var e=this.props,t=e.dispatch,a=e.id,r=void 0===a?-1:a;t({type:"tabpages/queryOrderInfo",payload:{id:r}})}},{key:"render",value:function(){var e=this.props,t=e.orderInfo,a=void 0===t?{}:t,r=e.loading,d=(a.id,a.orderSeq),l=void 0===d?"":d,i=a.startTime,o=void 0===i?"":i,u=a.completeTime,s=void 0===u?"":u,c=a.bidPrice,m=void 0===c?"":c,p=a.dealerName,y=void 0===p?"":p,v=a.dealerStore,h=void 0===v?"":v,I=a.dealerMobile,E=void 0===I?"":I,k=a.dealerProvince,q=void 0===k?"":k,C=a.dealerCity,T=void 0===C?"":C,P=a.dealerDistrict,x=void 0===P?"":P,S=a.dealerAddress,w=void 0===S?"":S,B=a.bidderName,F=void 0===B?"":B,M=a.bidderStore,O=void 0===M?"":M,V=a.bidderMobile,R=void 0===V?"":V,j=a.bidderProvince,N=void 0===j?"":j,L=a.bidderCity,z=void 0===L?"":L,D=a.bidderDistrict,A=void 0===D?"":D,_=a.bidderAddress,H=void 0===_?"":_;return f.default.createElement("div",null,f.default.createElement(n.default,{bordered:!0,title:"\u8ba2\u5355\u57fa\u672c\u4fe1\u606f",loading:r,style:{marginBottom:16}},f.default.createElement(b.default,{col:2},f.default.createElement(g,{term:"\u8ba2\u5355\u7f16\u53f7"},l),f.default.createElement(g,{term:"\u7ade\u4ef7\u5f00\u59cb\u65f6\u95f4"},o),f.default.createElement(g,{term:"\u7ade\u4ef7\u6210\u4ea4\u65f6\u95f4"},s),f.default.createElement(g,{term:"\u7ade\u4ef7\u6210\u4ea4\u91d1\u989d"},m,"\u5143"))),f.default.createElement(n.default,{bordered:!0,title:"\u95e8\u5e97\u4fe1\u606f",loading:r,style:{marginBottom:32}},f.default.createElement(b.default,{col:2,style:{marginBottom:16}},f.default.createElement(g,{term:"\u552e\u5356\u65b9\u59d3\u540d"},y),f.default.createElement(g,{term:"\u552e\u5356\u65b9\u95e8\u5e97"},h),f.default.createElement(g,{term:"\u8054\u7cfb\u7535\u8bdd"},E),f.default.createElement(g,{term:"\u552e\u5356\u95e8\u5e97\u5730\u5740"},"".concat(q," ").concat(T," ").concat(x," ").concat(w))),f.default.createElement(b.default,{col:2,style:{marginBottom:32}},f.default.createElement(g,{term:"\u7ade\u4ef7\u65b9\u59d3\u540d"},F),f.default.createElement(g,{term:"\u7ade\u4ef7\u65b9\u95e8\u5e97"},O),f.default.createElement(g,{term:"\u7ade\u4ef7\u7535\u8bdd"},R),f.default.createElement(g,{term:"\u7ade\u4ef7\u95e8\u5e97\u5730\u5740"},"".concat(N," ").concat(z," ").concat(A," ").concat(H)))))}}]),t}(f.PureComponent))||i),v=y;t.default=v},cEAO:function(e,t,a){"use strict";var r=a("g09b"),d=a("tAuX");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("bbsP");var l=r(a("/wGt"));a("IzEo");var i=r(a("bx4M")),n=r(a("gWZ8"));a("14J3");var o=r(a("BMrR"));a("P2fV");var u=r(a("NJEC"));a("jCWc");var s=r(a("kPKH"));a("+L6B");var c=r(a("2/Rp")),m=r(a("p0pE")),f=r(a("2Taf")),p=r(a("vZ4D")),b=r(a("l4Ni")),g=r(a("ujKo")),y=r(a("rlhR")),v=r(a("MhPg"));a("y8nQ");var h,I,E,k,q=r(a("Vl3Y")),C=d(a("q1tI")),T=a("MuoO"),P=(r(a("mOP9")),r(a("BkRI")),r(a("zHco"))),x=r(a("JpOw")),S=r(a("n+/z")),w=r(a("ug3m")),B=a("+n12"),F=r(a("2Ml+")),M=r(a("TEpi")),O=(h=(0,T.connect)(function(e){var t=e.orderlistmanage,a=e.loading;return{orderlistmanage:t,loading:a.models.orderlistmanage}}),I=q.default.create(),h(E=I((k=function(e){function t(){var e,a;(0,f.default)(this,t);for(var r=arguments.length,d=new Array(r),l=0;l<r;l++)d[l]=arguments[l];return a=(0,b.default)(this,(e=(0,g.default)(t)).call.apply(e,[this].concat(d))),a.pageConfig=(0,F.default)((0,y.default)(a)),a.state={drawerVisible:!1,currentId:-1,currentItem:{},showModalType:"",formValues:{},queryValues:{},detailFormItems:[]},a.queryParamsFormater=function(e,t){var r=a.props.orderlistmanage.data.pagination;delete r.total;var d={form:{},query:{},pagination:{current:1,pageSize:10}};switch(t){case 1:Object.assign(d,{query:(0,m.default)({},e)});break;case 2:Object.assign(d,{query:(0,m.default)({},a.state.queryValues),form:(0,m.default)({},e),pagination:r});break;case 3:Object.assign(d,{form:(0,m.default)({},e)});break;case 4:Object.assign(d,{query:(0,m.default)({},a.state.queryValues),pagination:{current:e.page,pageSize:e.pageSize}});break;default:Object.assign(d,{})}return d},a.showDrawer=function(e){a.setState({drawerVisible:!0,currentId:e.id,currentItem:e})},a.hideDrawer=function(){a.setState({drawerVisible:!1}),a.props.dispatch({type:"orderlistmanage/fetch",payload:a.queryParamsFormater()})},a.extraTableColumnRender=function(){var e=[{title:"\u64cd\u4f5c",width:120,fixed:"right",render:function(e){var t=e.id,r=e.status;return C.default.createElement(o.default,{type:"flex",justify:"space-between"},C.default.createElement(s.default,{span:24,style:{marginBottom:12}},C.default.createElement(c.default,{onClick:function(){a.showDrawer(e)},type:"primary",size:"small"},"\u67e5\u770b\u8be6\u60c5")),3===r?C.default.createElement(s.default,{span:24},C.default.createElement(u.default,{title:"\u786e\u5b9a\u5173\u95ed\u8ba2\u5355\u5417\uff1f",onConfirm:function(){a.closeOrderHandle(t)}},C.default.createElement(c.default,{type:"danger",size:"small"},"\u5173\u95ed\u8ba2\u5355"))):null)}}];return e},a.renderSearchForm=function(){var e=a.props,t=e.form,r=e.dispatch,d=a.pageConfig.searchForms,l={form:t,formInfo:{layout:"inline",formItems:d},handleSearchSubmit:function(e){var t=e.createdTime;delete e.region;var d=Object.assign(e,{createdTime:(0,B.dateFormatter)(t)}),l=(0,B.formaterObjectValue)(d);a.setState({queryValues:l}),r({type:"orderlistmanage/fetch",payload:a.queryParamsFormater(l,1)})},handleFormReset:function(){var e=l.formInfo.formItems;Object.assign(l,{formInfo:{formItems:(0,B.formItemRemoveInitValue)(e)}}),a.setState({queryValues:{}}),r({type:"orderlistmanage/fetch",payload:a.queryParamsFormater()})}};return C.default.createElement(x.default,l)},a.renderTable=function(){var e=a.props,t=e.orderlistmanage,r=e.loading,d=a.pageConfig.tableColumns,l=t.data,i=l.list,o=l.pagination,u=[].concat((0,n.default)(d),(0,n.default)(a.extraTableColumnRender())),s={loading:r,dataSource:i,columns:u,pagination:Object.assign(o,{pageSize:10}),handleTableChange:function(e){var t=e.current,r=a.props.dispatch,d=a.state.formValues,l=(0,m.default)({page:t,pageSize:10},d);r({type:"orderlistmanage/fetch",payload:a.queryParamsFormater(l,4)})},bordered:!1,otherProps:{scroll:{x:1400}}};return C.default.createElement(S.default,s)},a}return(0,v.default)(t,e),(0,p.default)(t,[{key:"componentDidMount",value:function(){var e=this.props.dispatch;e({type:"orderlistmanage/fetch",payload:this.queryParamsFormater()})}},{key:"closeOrderHandle",value:function(e){this.props.dispatch({type:"orderlistmanage/deleteOrder",payload:this.queryParamsFormater({id:e,isDeleted:1},2)})}},{key:"render",value:function(){var e=this.state,t=(e.detailFormItems,e.showModalType,e.drawerVisible),a=e.currentId,r=e.currentItem,d=this.props.orderlistmanage;d.modalVisible,d.confirmLoading;return C.default.createElement(P.default,null,C.default.createElement(i.default,{bordered:!1},C.default.createElement("div",{className:M.default.tableList},C.default.createElement("div",{className:M.default.tableListForm},this.renderSearchForm(),this.renderTable()))),C.default.createElement(l.default,{destroyOnClose:!0,title:"\u7ade\u4ef7\u65b9\u8be6\u7ec6\u4fe1\u606f",width:"80%",placement:"right",onClose:this.hideDrawer,maskClosable:!1,visible:t,zIndex:100,style:{height:"calc(100% - 55px)",overflow:"auto"}},C.default.createElement(w.default,{currentId:a,currentItem:r})))}}]),t}(C.PureComponent),E=k))||E)||E);t.default=O},ug3m:function(e,t,a){"use strict";var r=a("g09b");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0,a("Znn+");var d=r(a("ZTPi")),l=r(a("q1tI")),i=r(a("BDxJ")),n=r(a("Z6j4")),o=r(a("Nx2e")),u=d.default.TabPane,s=function(e){var t=e.currentId,a=e.currentItem,r=void 0===a?{}:a,s=r.status,c=r.reportUrl,m=void 0===c?"":c;return l.default.createElement(d.default,{defaultActiveKey:"1",animated:!1},l.default.createElement(u,{tab:"\u8f66300\u8bc4\u4f30\u4fe1\u606f",key:"1"},l.default.createElement(i.default,{url:m})),l.default.createElement(u,{tab:"\u8ba2\u5355\u8be6\u7ec6\u4fe1\u606f",key:"3"},l.default.createElement(n.default,{id:t})),1!==s&&l.default.createElement(u,{tab:"\u7ade\u4ef7\u8be6\u7ec6\u4fe1\u606f",key:"2"},l.default.createElement(o.default,{orderId:t,currentItem:r})))},c=s;t.default=c}}]);