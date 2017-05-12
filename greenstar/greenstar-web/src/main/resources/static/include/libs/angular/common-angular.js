define(['angular',"jquery","tmPagination",'uiBootstrap',"layer"
        ],function(angular,jQuery,a,b,l){
	function Utils(){}
	Utils.createAngularModule = function( moduleName ){
		 var module = angular.module( moduleName || 'default.module', ['tm.pagination','ui.bootstrap'] );
		//97日期选择
		 module.directive('datePicker', function () {
	         return {
	             restrict: 'A',
	             require: 'ngModel',
	             scope: {
	                 minDate: '@',
	             },
	             link: function (scope, element, attr, ngModel) {

	                 element.val(ngModel.$viewValue);

	                 function onpicking(dp) {
	                     var date = dp.cal.getNewDateStr();
	                     scope.$apply(function () {
	                         ngModel.$setViewValue(date);
	                     });
	                 }
	                 
	                 element.bind('click', function () {
	                	 var pickerOption = {
	                			 onpicking: onpicking,
	                			 isShowClear:false,
	                			 dateFmt: attr.datefmt || 'yyyy-MM-dd'
	                	 };
	                	 if(attr.mindate) {
	                		 pickerOption.minDate = attr.mindate;
	                	 }
	                	 if(attr.maxdate) {
	                		 pickerOption.maxDate = attr.maxdate;
	                	 }
	                     WdatePicker(pickerOption)
	                 });
	             }
	         };
	     });
		 
		 //ueditor富文本编辑器
		 module.directive('ueditor', [ '$sce', function ($sce) { 
			    return { 
			        restrict: 'A', 
			        require: 'ngModel', 
			        link: function (scope, element, attrs, ctrl) { 
			            var id = 'ueditor_'+new Date().getTime(); 
			            element[0].id = id; 
			            var ue = UM.getEditor(id, {
			                autoHeightEnabled: false 
			            }); 
			            ue.ready(function () { 
			            	if(scope.form.remark) {
			            		var text = html_decode(scope.form.remark || '');
			            		ue.setContent(text);
			            	}
			            	if(scope.form.aboutContent) {
			            		var text = html_decode(scope.form.aboutContent || '');
			            		ue.setContent(text);
			            	}
			            	ctrl.$setViewValue(ue.getContent()); 
			                ue.addListener('contentChange', function () { 
			                    ctrl.$setViewValue(ue.getContent()); 
			                    if (!scope.$$phase) { 
			                        scope.$apply(); 
			                    } 
			               }); 
			           }); 
			        } 
			    }; 
			}]);

		 
		//数据字典过滤器  
		 module.filter('dataDicDescFilter',function(){
	        return function(code, dataType){  
	            return getDataDicDesc(dataType, code);  
	        }  
	    })  
	    //数据字典过滤绑定model
	    module.directive('datadicFilter', ['$filter',function($filter) { 
	        return {  
	            require: 'ngModel',  
	            link: function(scope, elm, attrs, ctrl) {  
	            	var dataType = attrs.datadicFilter;
	            	ctrl.$formatters.push(function(modelValue){
	                    if(typeof modelValue != "undefined"){
	                        //返回字符串给view,不改变模型值
	                        return getDataDicDesc(dataType, modelValue); 
	                    }
	                })
	            }  
	        };  
	    }]); 

        /* 
         * 根据数据字典编码返回描述，如果存在则返回对于描述，否则返回code本身
         */
        function getDataDicDesc(dataType, code){
        	var result = "";
        	if(undefined != window.dataDictionaryGroupData) {
        		$(window.dataDictionaryGroupData[dataType]).each(function(i, data){
        			if(data.code == code) {
        				result = data.descpt;
        				return;
        			}
        		});
        	}
        	return result;
        }
		 
					 
		 //输出html
		module.filter('to_trusted', [ '$sce', function($sce) {
			return function(text) {
				text = html_decode(text || '');
				return $sce.trustAsHtml(text);
			};
		} ]);
		window.html_decode = function(str) {
			var s = "";
			if (undefined == str || str.length == 0)
				return "";
			s = str.replace(/amp;/g, "");
			s = s.replace(/&lt;/g, "<");
			s = s.replace(/&amp;lt;/g, "<");
			s = s.replace(/&gt;/g, ">");
			s = s.replace(/&amp;gt;/g, ">");
			s = s.replace(/&nbsp;/g, " ");
			s = s.replace(/&#39;/g, "\'");
			s = s.replace(/&quot;/g, "\"");
			return s;
		}
		 return module;
	}
	
	   /**
     * 控制器管理页面基类
     * @param $scope
     * @param prefixUrl {String} 各操作的url 前缀
     * @constructor
     */

    Utils.AngularBaseCtrl = function ( $scope,$http,$sce) {
    	$scope.form = {};// 一些提交的数据分类
	    $scope.params = {}; // 搜索的参数
	    $scope.params['pageNumber'] = 1;
	    $scope.params['pageSize'] = 15;
	   	// 是否全选
        $scope.form.allCheckbox = false;
    	
    	// 全选操作
    	$scope.allCheckbox = function () {
    		if($scope.result && $scope.result.data) {
    			for ( var i = 0; i < $scope.result.data.length; i++ ) {
    				$scope.result.data[ i ].checkbox = !$scope.form.allCheckbox;
    			}
    			$scope.form.checkbox = !$scope.form.allCheckbox;
    		}
        };

        // 是否勾选了至少一个
        $scope.isCheckbox = function () {
            var allCheckbox = true,checkbox = false;
            for ( var i = 0; i < $scope.result.data.length; i++ ){
                var check = $scope.result.data[ i ].checkbox;
                allCheckbox = check ? allCheckbox : false;
                checkbox = check || checkbox;
            }
            $scope.form.allCheckbox = allCheckbox;
            return $scope.form.checkbox = checkbox;
        };
        
    	//检查表单属性
		$scope.checkFormEle = function(ele) {
			var validate = true;
			if (undefined == ele || ele.length == 0) {
				validate = false;
			}
			return validate;
		}	
        
        /**
         * 提交请求
         */
        $scope.postRequest = function(options, data, callback) {
    		var loadInx = layer.load();
    		$http({
    			method: 'POST' ,
    			url: options.postUrl ,
    			data: $.param(data), // pass in data as strings
    			headers: { 'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8; ajax' }
    		}).success( function (resp) {
    			layer.close(loadInx);
    			
    			if(resp.state == 301) {
    				layer.msg("您尚未登录或登录时间过长,请重新登录!");
    				setTimeout(function(){
    					window.location.reload();
    				}, 500);
    				return ;
    			}
    			
    			if(undefined != callback) {
    				callback(resp);
    			}
    		}).error(function() {
    			layer.close("操作失败");
    		});
        }
        

        //查询数据
    	$scope.queryResult = function(options, callback){
    		
    		if(options.initPage) {
    			$scope.params['pageNumber'] = options.pageNumber||1;
    			$scope.params['pageSize'] = options.pageSize||15;
    		}
    		
    		$scope.postRequest({"postUrl":options.listUrl}, $scope.params, function(resp){
    			$scope.paginationConf.totalItems = resp.totalElements;
    			$scope.result = resp;
    			$scope.query_options = options;
    			$scope.query_callback = callback;
    			if(callback && undefined != callback) {
    				callback(resp);
    			}
    		});
    	}
    	
    	/**
    	 * 确认弹框
    	 */
    	$scope.comfirmWin = function(txt, yesCallback, cancelCallback) {
    		var inx = layer.confirm(txt, {
                btn: ['确定','取消'], //按钮
                scrollbar: false,
                shadeClose:true
            }, yesCallback(inx), cancelCallback(inx));
    	}
        
        /**
         * 删除
         * idN：主键名称
         * idN：主键值
         * allFlag：是否批量删除
         */
        $scope.doDelete = function(options, callback) {
        	var ids = [];
        	var delNumTxt = "";
        	if(options.allFlag) {//删除多条数据
        		for ( var i = 0; i < $scope.result.data.length; i++ ) {
        			var curObj = $scope.result.data[ i ];
        			curObj.checkbox && ids.push( curObj[ options.idN||'id'] );
        		}
        		delNumTxt = ids.length + "条数据";
        	}else {
        		ids.push(options.idV);
        	}
        	if(ids.length == 0) {
        		layer.msg("请选择删除的数据");
        		return false;
        	}
        	$scope.params.ids = ids;
        	var inx = layer.confirm('您确认删除'+delNumTxt+'?', {
                btn: ['确定','取消'], //按钮
                scrollbar: false,
                shadeClose:true
            }, delYes);
            function delYes(){
                layer.close(inx);
                
                var loadInx = layer.load();
                $scope.postRequest({"postUrl":options.deleteUrl}, $scope.params, function(resp){
                	
                	$scope.form.allCheckbox = false;
                	$scope.allCheckbox();
                	if(undefined != callback) {
                		callback(resp);
                	}else {
                		if(resp.success) {
                			layer.msg("删除成功！");
                			$scope.params['pageNumber'] = $scope.paginationConf.currentPage;
                			$scope.params['pageSize'] = $scope.paginationConf.itemsPerPage;
                			$scope.queryResult($scope.callback);
                		}else {
                			layer.msg(resp.message);
                		}
                	}
                });
            }
        }
        
      //加载后台页面
        $scope.loadLayerPage = function(url, params, historyParamsFlag) {
			window.loadLinked(url, params, historyParamsFlag);
        }
        
        
        /**
         * 拷贝对象 拼装参数
         * object 解析的对象
         * objectType 对象类型 1：普通对象 2：数组对象
         * paramsObject 参数对象
         * paramsPrex 参数前缀
         */
        $scope.copyObjectPropertiesToParams = function(object, objectType, paramsObject, paramsPrex) {
        	switch(objectType) {
        	case 1:
        		var objectCopy = angular.copy(object);
    			for(var key in objectCopy){
    				paramsObject[paramsPrex + '.' + key] = objectCopy[key];
    			}
        		break;
        	case 2:
        		$(object).each(function(i, data){
    				for(var key in data) {
    					paramsObject[paramsPrex + "["+ i +"]." + key] = data[key];
    				}
    			})
        		break;
        	}
        }
        
        /***************************数组操作**********************************/
        Array.prototype.remove=function(dx) 
        { 
            if(isNaN(dx)||dx>this.length){return false;} 
            for(var i=0,n=0;i<this.length;i++) 
            { 
                if(this[i]!=this[dx]) 
                { 
                    this[n++]=this[i] 
                } 
            } 
            this.length-=1 
        }
        
    	/**
    	 * 对话框应用
    	 * 
    	 */
    	$scope.items = ['item1', 'item2', 'item3'];  
    	$scope.open = function () {  
             var modalInstance = $modal.open({  
                 templateUrl: options.mnodelUrl,  
                 controller: ModalInstanceCtrl,  
                 resolve: {  
                     items: function () {  
                         return $scope.items;  
                     }  
                 }  
             });  
             modalInstance.opened.then(function(){//模态窗口打开之后执行的函数  
                 console.log('modal is opened');  
             });  
            modalInstance.result.then(function (result) {  
                  console.log(result);  
             }, function (reason) {  
                 console.log(reason);//点击空白区域，总会输出backdrop click，点击取消，则会暑促cancel  
                 $log.info('Modal dismissed at: ' + new Date());  
             });  
    	  };  
    	/**
         * I'm not good at English, wish you you catch what I said And help me improve my English.
         *
         * A lightweight and useful pagination directive
         * conf is a object, it has attributes like below:
         *
         * currentPage: Current page number, default 1
         * totalItems: Total number of items in all pages
         * itemsPerPage:  number of items per page, default 15
         * onChange: when the pagination is change, it will excute the function.
         *
         * pagesLength: number for pagination size, default 9
         * perPageOptions: defind select how many items in a page, default [10, 15, 20, 30, 50]
         * rememberPerPage: use to remember how many items in a page select by user.
         *
         */
    	$scope.paginationConf = {
    	            currentPage: $scope.params['pageNumber'],
    	            totalItems: 0,
    	            itemsPerPage: $scope.params['pageSize'],
    	            pagesLength: $scope.params['pageSize'],
    	            perPageOptions: [10, 15, 20, 25, 30, 50],
//    	            rememberPerPage: 'perPageItems',
    	            onChange: function(){
    	            	if(($scope.paginationConf.currentPage != 0 && $scope.params['pageNumber'] != $scope.paginationConf.currentPage) || 
    	            			$scope.params['pageSize'] != $scope.paginationConf.itemsPerPage) {//防止初始化时被调用
    	            		$scope.params['pageNumber'] = $scope.paginationConf.currentPage;
    	        			$scope.params['pageSize'] = $scope.paginationConf.itemsPerPage;
    	            		$scope.queryResult($scope.query_options, $scope.query_callback);
    	            	}
    	            }
         };
	    /***************************************************************************
		 * 定义模态对话框
		 * 
		 */
    	var ModalInstanceCtrl = function ($scope, $modalInstance, items) {  
	         $scope.items = items;  
	         $scope.selected = {  
	             item: $scope.items[0]  
	         };  
	         $scope.ok = function () {  
	             $modalInstance.close($scope.selected);  
	         };  
	         $scope.cancel = function () {  
	             $modalInstance.dismiss('cancel');  
	         };  
    	}; 
    };
	
    return Utils;
});