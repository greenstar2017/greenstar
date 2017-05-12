requirejs.config({
	urlArgs: "v=" + (new Date()).getTime(),
	//urlArgs: "version="+window.VERSION,
	baseUrl: (window.CTX || '')+'/include',
	paths: {
		jquery: "libs/jquery/jquery-1.10.2.min",
		validate_lib: "libs/jquery-validation/1.11.1/jquery.validate.min",
		angular: "libs/angular/angular.min",
		angularRoute: "libs/angular/angular-route.min",
		tmPagination: "libs/angularjs/tm.pagination",
		uiBootstrap: "libs/angularjs/angular-ui-bootstrap",
		bootstrap: "libs/bootstrap/js/bootstrap.min",
		bootstrap3: "libs/bootstrap3/js/bootstrap.min",
		validate: "libs/jquery-validation/1.11.1/messages_bs_zh",
		ajaxfileupload: "libs/ajaxfileupload/ajaxfileupload.min",
		ueditorConfig: "libs/ueditor/ueditor.config",
		ueditor:"libs/ueditor/ueditor.all.min",
		umeditorConfig: "libs/umeditor/umeditor.config",
		umeditor:"libs/umeditor/umeditor",
		fileupload:"libs/ueditor/fileupload",
	    layer:"libs/layer/layer",
	    uiSelect:"libs/ui-select/ui-select",
	    localStorage:"libs/local-storage/localStorage",
	    commonAngular:"libs/angular/common-angular",
	    mathutils:"libs/math/js/math_utils",
	    auto_dialog:"common/js/dialog",
	    nav:"common/js/nav"
	},
	map: {
		  '*': {
		    'css': 'libs/require-css/css' // or whatever the path to require-css is
		  }
		},
	shim: {
		'jquery': {
			deps: ['libs/iefix'],
			exports: 'jQuery'
		},  
		'ajaxfileupload': {
			deps: ['jquery']
		},  
		'validate': {
			deps: ['validate_lib','css!libs/jquery-validation/1.11.1/validate.css'],
			exports: 'jQuery'
		},
		'bootstrap':{
			deps: ['css!libs/bootstrap/css/bootstrap.min','css!libs/bootstrap/css/bootstrap-responsive.min','jquery'],
			exports: 'jQuery'
		},
		'bootstrap3':{
			deps: ['css!libs/bootstrap3/css/bootstrap.min','jquery'],
			exports: 'jQuery'
		},
		'layer': {
			deps: ['css!libs/layer/skin/layer','jquery'],
			exports: 'layer'
		},
		'localStorage': {
			deps: ['jquery'],
			exports: 'localStorage'
		},
		'uiSelect': {
			deps: ['css!libs/ui-select/selectlist.css','jquery'],
			exports: 'uiSelect'
		},
		'angular': {
            exports: 'angular'
        },
        'angularRoute':{
            exports:"angular-route"
        },
        'tmPagination':{
        	deps: ['css!libs/angularjs/tmPagination','angular'],
            exports:"tmPagination"
        },
        'uiBootstrap':{
        	deps: ['angular'],
            exports:"uiBootstrap"
        },
		'umeditor':{
			deps:['css!libs/umeditor/themes/default/css/umeditor','jquery'],
			exports: 'umeditor'
		},
        'fileupload': {
			deps: ['jquery']
		},
		'nav': {
			deps: ['jquery']
		}
	},
	 priority: [
	            "angular"
	           ]
});

