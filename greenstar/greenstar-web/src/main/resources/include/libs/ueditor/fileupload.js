/**
 * 文件上传jquery插件
 */
!function ($) {
  "use strict"; // jshint ;_;
  
  function FileUpload(element, options) {
    var $element = $(element).is('body') ? $(window) : $(element);
    this.options = $.extend({}, $.fn.fileupload.defaults, options);
    this.$element = $element;
    this.baseUrl= this.options.webroot+"/include/libs/ueditor/dialogs/image/image.html";
    this.init(this);
    var self=this;
    $element.click(function(){
    	self._btnopen();
    });
  }

  FileUpload.prototype = {
    constructor: FileUpload,
    //初始化加载dom元素
    init: function (thsObj) {
    	var $body = $(document.body);
    	var $dialog = $body.find("#file_upload_dialog");
    	var self=thsObj;
    	//初始化dialog
    	if(!$dialog.length){
    		var domHtml=[];
    		domHtml.push('<div class="modal fade in" id="file_upload_dialog" tabindex="-1" role="dialog" aria-hidden="false" style="display: none;"><div class="modal-dialog" style="width:658px;""><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h4 class="modal-title">图片选择</h4></div><div class="modal-body" style="padding:10px;"><iframe frameborder="0" scrolling="no" style="width:635px;height:380px;overflow:none;"></iframe></div><div class="modal-footer" style="margin-top:0;"><button type="button" class="btn btn-primary btn_do">确认</button><button type="button" class="btn btn-default btn_del">清除</button><button type="button" class="btn btn-default btn_cancle">取消</button></div></div></div></div>');
    		$body.append(domHtml.join(""));
    		$dialog = $("#file_upload_dialog");
    		$dialog.on("click.ok","button.btn_do",function(e){//点击确定
    			self._btndo();
    		}).on("click.del","button.btn_del",function(e){
    			self.options.callback(self.options.multi?[]:null);
    	    	$dialog.modal('hide');
    		}).on("click.cancle","button.btn_cancle",function(e){
    	    	$dialog.modal('hide');
    		});
    	}
    },
  //直接调用公共方法
    openChooseDiaglog:function(options){
    	var thsObj={};
    	thsObj.options = $.extend({}, $.fn.fileupload.defaults, options); 
        thsObj.baseUrl= thsObj.options.webroot+"/include/libs/ueditor/dialogs/image/image.html";
        thsObj._btndo = FileUpload.prototype._btndo;
    	FileUpload.prototype.init(thsObj);
    	FileUpload.prototype._btnopen(thsObj);
    },
    _btnopen:function(thsObj){
    	var $dialog = $("#file_upload_dialog");
    	var self = thsObj||this;
    	$dialog.data("fileupload-obj",self);//留待确定和清除的时候用
    	var iframe=$dialog.find("iframe");
    	iframe.attr("src",self.baseUrl);
    	//定义全局文件上传配置项
        window.$fileupload_opts={
    		webroot: self.options.webroot,
    		initTab:self.options.initTab,
    	    multi:self.options.multi,
    	    fileListSize:self.options.fileListSize,
    	    fileMaxSize:self.options.fileMaxSize,
    	    getSel:function(){},
    	    acceptSuffix:self.options.acceptSuffix
        }
    	//展开模态窗口
    	$dialog.modal({backdrop:'static'});
    },
    _btndo:function(){
    	var $dialog = $("#file_upload_dialog");
    	var self = $dialog.data("fileupload-obj");
    	if(self.options.callback){
    		var res=$fileupload_opts.getSel();
    		self.options.callback(res);
    	}
    	$("#file_upload_dialog").modal('hide');
    }
  }


 /* fileupload PLUGIN DEFINITION
  * =========================== */


  $.fn.fileupload = function (option) {
	var newArgs = Array.prototype.slice.call(arguments, 1); 
    return this.each(function () {
      var $this = $(this)
        , data = $this.data('fileupload')
        , options = typeof option == 'object' && option
      if (!data) $this.data('fileupload', (data = new FileUpload(this, options)))
      if (typeof option == 'string') data[option].apply(data, newArgs);
    })
  }

  $.fn.fileupload.Constructor = FileUpload;

  $.fn.fileupload.defaults = {
    webroot: CTX||"",
    initTab:'upload',//初始化显示上传面板upload 还是在线选择面板online
    multi:false,//默认为单选
    fileListSize:20,//在线文件每次显示20个 ,后台配置这里修改无效
    fileMaxSize:1024000,//上传文件最大size,后台配置这里修改无效
    acceptSuffix:"png,jpg,jpeg,gif,bmp"//允许上传文件后缀,后台配置这里修改无效
  }
  //提供直接调用api
  $.fn.fileupload.openChooseDiaglog = FileUpload.prototype.openChooseDiaglog;
}(window.jQuery);
