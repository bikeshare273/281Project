var app = angular.module('app', [ 'ngRoute', 'ngResource', 'smart-table' ]);

app.run(function($rootScope) {
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;
});

//factory for sharing data between controller
app.factory('dataSharing', function() {
	var sharedData = {}
	function set(data) {
		sharedData = data;
	}
	function get() {
		return sharedData;
	}

	return {
		set : set,
		get : get
	}

});

app.directive('passwordMatch', [ function() {
	return {
		restrict : 'A',
		scope : true,
		require : 'ngModel',
		link : function(scope, elem, attrs, control) {
			var checker = function() {

				//get the value of the first password
				var e1 = scope.$eval(attrs.ngModel);

				//get the value of the other password  
				var e2 = scope.$eval(attrs.passwordMatch);
				return e1 == e2;
			};
			scope.$watch(checker, function(n) {

				//set the form control to valid if both 
				//passwords are the same, else invalid
				control.$setValidity("unique", n);
			});
		}
	};
} ]);

// configure our routes
app.config(function($routeProvider) {
	$routeProvider

	// route for the welcome page
	.when('/', {
		templateUrl : 'home.html',
		controller : 'homeController'
	})

	.when('/register', {
		templateUrl : 'register.html',
		controller : 'registerController'
	})

	.when('/home', {
		templateUrl : 'HomeUser.html',
		controller : 'homeUserController'
	})

	.otherwise({
		redirectTo : '/'
	});
});

app.controller('homeController', function($scope, $http, $location, $q,
		dataSharing, $timeout, $rootScope) {
	console.log('homeController start');
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;

	$scope.loginform_login = function(item, event) {
		console.log("--> Submitting form " + $scope.loginform_email + " "
				+ $scope.loginform_password);
		console.log("--> Submitting form ");
		
		$location.url('/home');
	};

	$scope.clickRegister = function() {
		$location.url('/register');
	}

	console.log('homeController end');
});

app.controller('registerController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('registerController start');
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;
	
	$scope.signupform_signup = function(item, event) {
		console.log("--> Submitting form "
				+ $scope.signupform_name + " "
				+ $scope.signupform_email );
		console.log("--> Submitting form "
				+ $scope.signupform_phone + " "
				+ $scope.signupform_address);
		console.log("--> Submitting form "
				+ $scope.signupform_city + " "
				+ $scope.signupform_state+" "
				+ $scope.signupform_country);
		console.log("--> Submitting form ");
		
	};
	console.log('registerController end');
});

app.controller('homeUserController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('homeUserController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	
	console.log('homeUserController end');
});


