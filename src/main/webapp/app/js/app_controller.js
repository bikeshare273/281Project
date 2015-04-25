var movieapp = angular.module('movieapp', [ 'ngRoute', 'ngResource']);

movieapp.run(function($rootScope) {
    $rootScope.OffNavTabs = true;
    $rootScope.OffLogoutTabs = true;
});

//factory for sharing data between controller
movieapp.factory('dataSharing', function() {
	 var sharedData = {}
	 function set(data) {
		 sharedData = data;
	 }
	 function get() {
		 return sharedData;
	 }

	 return {
	  set: set,
	  get: get
	 }

});

// configure our routes
movieapp.config(function($routeProvider) {
	$routeProvider

	// route for the welcome page
	.when('/', {
		templateUrl : 'home.html',
		controller : 'homeController'
	})
	
	.otherwise({
		redirectTo : '/'
	});
});

movieapp.controller('homeController',
	 				function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('homeController start');
	
	$scope.submitWelcomeMessage = function(){
		console.log("username "+$scope.username);
		//submit welcome message
		var response = $http.get("../api/v1/demo/"+$scope.username);
		console.log(response);
		response.success(function(dataFromServer, status,
						headers, config) {
			console.log("dataFromServer" + dataFromServer);
					if (dataFromServer) {
						//validated go ahead
						$scope.welcomemessage = dataFromServer.message;
					}else{
						$location.url('/');
					}
				});
		response.error(function(data, status, headers, config) {
			if (response.status === 401
					|| response.status === 400) {
				$scope.loginform_error = "Invalid request";
				$location.url('/');
				return $q.reject(response);
			}
		});
	}


	console.log('homeController end');
});
