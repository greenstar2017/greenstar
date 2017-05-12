require(["jquery",'commonAngular'],function(jQuery,Utils){
	
	var module = Utils.createAngularModule();
	module.controller( "ngController", function ( $scope,$http,$sce) {
		
		Utils.AngularBaseCtrl.call( this, $scope,$http,$sce);
		$scope.queryResult({'listUrl':window.CTX + "/demo/find"});
	})
	angular.bootstrap( $("#ngDivId"), [ 'default.module' ] );
	
	$("#ngDivId").show();
});