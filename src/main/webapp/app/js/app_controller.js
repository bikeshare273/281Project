var app = angular.module('app', [ 'ngRoute', 'ngResource', 'smart-table', 'googlechart']);

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
	
	.when('/openproject', {
		templateUrl : 'project.html',
		controller : 'projectController'
	})
	
	.when('/opennewproject', {
		templateUrl : 'newproject.html',
		controller : 'projectnewController'
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
		console.log("--> Submitting form "
				+ $scope.loginform_email + " "
				+ $scope.loginform_password);
		console.log("--> Submitting form ");
		var data = {
			username : $scope.loginform_email,
			password : $scope.loginform_password
		};

		var response = $http.post("../../api/v1/login", data,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					$location.url('/home');
				});
		response.error(function(data, status, headers, config) {
			if (response.status === 401
					|| response.status === 400) {
				$scope.loginform_error = "Invalid request";
				$location.url('/');
				return $q.reject(response);
			}
		});
	
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
				+ $scope.signupform_email + " "
				+ $scope.signupform_password );
		console.log("--> Submitting form "
				+ $scope.signupform_phone );
		console.log("--> Submitting form ");
		var data = {
				name : $scope.signupform_email,
				username : $scope.signupform_email,
				password: $scope.signupform_password,
			};
			var response = $http.post("../../api/v1/createuser", data,
					{});
			response
					.success(function(dataFromServer, status,
							headers, config) {
						$scope.signupform_success = "User created successfully";
					});
			response.error(function(data, status, headers, config) {
				if (response.status === 401
						|| response.status === 400) {
					$scope.error = "Invalid request";
					$location.url('/');
					return $q.reject(response);
				}
			});
		
		
	};
	console.log('registerController end');
});

app.controller('homeUserController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('homeUserController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	//get project list
	var data = {
			
	};
	var response = $http.get("../../api/v1/getprojectsbyuserid");
	response
			.success(function(dataFromServer, status,
					headers, config) {
				$scope.projectlist = dataFromServer;
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
	$scope.openProject = function(item, event) {
		console.log("--> Submitting form openProject "
				+ $scope.projectdropdown.text );
		console.log("projectdropdown "+$scope.projectdropdown);
		var data = new Array();
		data["projectid"] = $scope.projectdropdown;
		dataSharing.set(data);
		$location.url('/openproject');
		
	};
	
	//get tenant list
	data = {
			
		};
		var response = $http.get("../../api/v1/getAllTenants");
		response
				.success(function(dataFromServer, status,
						headers, config) {
					$scope.tenantlist = dataFromServer;
				});
		response.error(function(data, status, headers, config) {
			if (response.status === 401
					|| response.status === 400) {
				$scope.error = "Invalid request";
				$location.url('/');
				return $q.reject(response);
			}
		});
	
	/*dataFromServer = [
         			 	 {"text":"tenant_name1"},
	        			 {"text":"tenant_name2"},
	        			 {"text":"tenant_name3"},
	        			]
	$scope.tenantlist = dataFromServer;*/
	
	$scope.createProject = function(item, event) {
		console.log("--> Submitting form createProject "
				+ $scope.tenantdropdown);
		
		//create project
		var data = {
				tenantid : $scope.tenantdropdown,
			};
		console.log("create project "+$scope.tenantdropdown);
			var response = $http.post("../../api/v1/createproject", data,
					{});
			response
					.success(function(dataFromServer, status,
							headers, config) {
						console.log(dataFromServer.projectId);
						var data = new Array();
						data["projectid"] = dataFromServer.projectId;
						dataSharing.set(data);
						$location.url('/openproject');
					});
			response.error(function(data, status, headers, config) {
				if (response.status === 401
						|| response.status === 400) {
					$scope.error = "Invalid request";
					$location.url('/');
					return $q.reject(response);
				}
			});
		
		//$location.url('/opennewproject');
	};
	
	console.log('homeUserController end');
});

app.controller('projectController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope, $route) {
	console.log('projectController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.hideGraph = false;
	
	$scope.tableCounterOne = 0;
	
	//get project id
	var projectid = dataSharing.get().projectid;
	console.log("projectid received "+projectid);
	
	//fetch project status
	var statusData = {
			project_Id : projectid,
		};
		var response = $http.post("../../api/v1/getProjectStatus", statusData,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					console.log(dataFromServer);
					$scope.projectStatus = dataFromServer;
				});
		response.error(function(data, status, headers, config) {
			if (response.status === 401
					|| response.status === 400) {
				$scope.error = "Invalid request";
				$location.url('/');
				return $q.reject(response);
			}
		});
	
	//details about project
	/*var displayData = new Array();
	displayData[0] = {
			"tableName":"Tasks",
			"col_name":["task_id","task_name","task_priority"],
			"row":[["1","firsttask","high"],["2","secondtask","high"],["3","thirdtask","medium"],["4","fourthtask","low"]]
	};
	displayData[1] = {
			"tableName":"Resources",
			"col_name":["resource_id","resource_name"],
			"row":[["1","swap"],["2","vaibhav"]]
	};*/
	//fetch details about project
	var data = {
			searchString : projectid,
		};
		var response = $http.post("../../api/v1/getproject", data,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					console.log(dataFromServer);
					var displayData = dataFromServer;
					
					//initialize hide/unhide logic
					$scope.hideEdit = new Array();
					$scope.hideTextOnly = new Array();
					
					
					
					console.log("l "+displayData.length);
					for(var n=0; n<displayData.length; n++){
						$scope.hideEdit[n] = true;
						$scope.hideTextOnly[n] = false;
					}
					
					//push data into table
					 $scope.queue = {
				        transactions: []
				    };
					 for(var i=0; i<displayData.length; i++){
				    	console.log("displayData "+displayData[i]);
				    	 $scope.queue.transactions.push(displayData[i]);
				    }
					
					 
					//on click submit
					 $scope.updateDetails = function(tableid) {
						console.log("--> Submitting form updateDetails ");
						
						//fetch deatils related to project and pass to projectController
						console.log("tableid "+displayData[tableid].col_name);
						var rows_data = new Array();
						for(var i=0; i<displayData[tableid].row.length; i++){
							var map = new Object();
							var createKeyValueString = "{";
							for(var j=0; j<displayData[tableid].col_name.length; j++){
								map[displayData[tableid].col_name[j]] = displayData[tableid].row[i][j];
								//rows_data[i][displayData[tableid].col_name[j]] = displayData[tableid].row[i][j];
								createKeyValueString = createKeyValueString + "'" + displayData[tableid].col_name[j] +"':'" + displayData[tableid].row[i][j] +"'";
								if(j<displayData[tableid].col_name.length-1){
									createKeyValueString = createKeyValueString + ",";
								}
							}
							createKeyValueString = createKeyValueString + "}"
							//rows_data[i] = createKeyValueString;
							rows_data[i] = map;
						}
						console.log("rows_data "+rows_data);
						var updateData = {
								"tableName":displayData[tableid].tableName,
								"projectName":$scope.project_name,
								"tenantId":"",
								"projectId":projectid,
								"rows":rows_data
						}
						console.log("updateData "+updateData.tableName);
						
						//submit updated data
						console.log("update project "+$scope.tenantdropdown);
							var response = $http.post("../../api/v1/updateproject", updateData,
									{});
							response
									.success(function(dataFromServer, status,
											headers, config) {
										console.log(dataFromServer);
										$route.reload();
										/*var data = new Array();
										data["projectid"] = projectid;
										dataSharing.set(data);
										$location.url('/openproject');*/
									});
							response.error(function(data, status, headers, config) {
								if (response.status === 401
										|| response.status === 400) {
									$scope.error = "Invalid request";
									$location.url('/');
									return $q.reject(response);
								}
							});
						
					};
					 
					 $scope.deleteRow = function(tableid, rowid) {
							console.log("deleting row for table "+tableid+" "+rowid);
							$scope.hideEdit[tableid] = true;
							$scope.hideTextOnly[tableid] = false;
							console.log("deleting row for table length "+displayData[tableid].row.length);
							displayData[tableid].row.splice(rowid, 1);
							console.log("deleting row for table length after "+displayData[tableid].row.length);
							//$route.reload();
							$location.url('/openproject');
							console.log("deleting row for table "+displayData[tableid].row);
					};

					$scope.addRow = function(tableid) {
							console.log("adding row for table "+tableid);
							$scope.hideEdit[tableid] = true;
							$scope.hideTextOnly[tableid] = false;
							var rowdata = new Array();
							for(var i=0; i<displayData[tableid].col_name.length; i++){
								rowdata[i] = " ";
							}

							if(displayData[tableid].row == null){
								displayData[tableid].row = new Array();
							}
							displayData[tableid].row.splice(displayData[tableid].row.length, 0, rowdata);
							console.log("adding row for table length after "+displayData[tableid].row.length);
							//$route.reload();
							$location.url('/openproject');
							console.log("adding row for table "+displayData[tableid].row);
					};
					
				});
		response.error(function(data, status, headers, config) {
			if (response.status === 401
					|| response.status === 400) {
				$scope.error = "Invalid request";
				$location.url('/');
				return $q.reject(response);
			}
		});
	
		//open graph
		$scope.openGraph = function() {
			console.log("openGraph projectid "+projectid);
				
			//create project
			var data = {
					project_Id : projectid
					//project_Id : "P55667788"
				};
				var response = $http.post("../../api/v1/getProjectGraphData", data,
						{});
				response
						.success(function(dataFromServer, status,
								headers, config) {
							console.log(dataFromServer);
							
							$scope.chartObjects = new Array();
							for(var z=0; z<dataFromServer.length; z++){
								
								/*$scope.chart.type = $routeParams.chartType;*/
								var chartObject = new Object();
								if(z == 0){
										chartObject.options = {
									        'title': 'Visualize '+$scope.project_name+' progress'
									    }
								}else{
										chartObject.options = {
									        'title': 'Sprint '+dataFromServer[z].sprint_Id
									    }
								}
							    chartObject.type = dataFromServer[z].chartType;
							    var chartRows = new Array();
							    //colsGraph == rows
							    var colsGraph = new Array();
							    for(var i=0; i<dataFromServer[z].xData.length; i++){
							    	var tempArray = new Array();
							    	var tempObjTaskName = {
							    			v:dataFromServer[z].xData[i].rowName
							    	};
							    	tempArray[0] = tempObjTaskName;
							    	var tempObjTaskValue = {
							    			v:dataFromServer[z].xData[i].rowValue
							    	};
							    	tempArray[1] = tempObjTaskValue;
							    	var colObject = {
							    			c:tempArray
							    	}
							    	colsGraph[i] = colObject;
							    	console.log("in "+colObject);
							    }
							    console.log("colsGraph==rows-> "+colsGraph[0]);
							    chartObject.data = {
							    		"rows":colsGraph
							    };
							    chartObject.data = {
							    		"cols": [
							    		         {
							    		           "id": dataFromServer[z].yName,
							    		           "label": dataFromServer[z].yName,
							    		           "type": "string",
							    		           "p": {}
							    		         },
							    		         {
							    		           "id": dataFromServer[z].xName,
							    		           "label": dataFromServer[z].xName,
							    		           "type": "number",
							    		           "p": {}
							    		         }
							    		 ],
							    		"rows":colsGraph,
							    		"vAxis": {
							    		      "title": dataFromServer[z].xName
							    		      /*"gridlines": {
							    		        "count": 6
							    		      }*/
							    		    },
						    		    "hAxis": {
						    		      "title": dataFromServer[z].yName
						    		    }
							    };

							    $scope.chartObjects[z] = chartObject;
							}
													    
							$scope.hideGraph = false;
						});
				response.error(function(data, status, headers, config) {
					if (response.status === 401
							|| response.status === 400) {
						$scope.error = "Invalid request";
						$location.url('/');
						return $q.reject(response);
					}
				});
			
			//$location.url('/opennewproject');
		};
	
	
	console.log('projectController end');
});

app.controller('projectnewController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('projectnewController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	
	
	console.log('projectnewController end');
});


