(function() {
    'use strict';

    angular
        .module('prueba3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('restaurante-my-suffix', {
            parent: 'entity',
            url: '/restaurante-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.restaurante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/restaurante/restaurantesmySuffix.html',
                    controller: 'RestauranteMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('restaurante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('restaurante-my-suffix-detail', {
            parent: 'restaurante-my-suffix',
            url: '/restaurante-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.restaurante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/restaurante/restaurante-my-suffix-detail.html',
                    controller: 'RestauranteMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('restaurante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Restaurante', function($stateParams, Restaurante) {
                    return Restaurante.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'restaurante-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('restaurante-my-suffix-detail.edit', {
            parent: 'restaurante-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restaurante/restaurante-my-suffix-dialog.html',
                    controller: 'RestauranteMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Restaurante', function(Restaurante) {
                            return Restaurante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('restaurante-my-suffix.new', {
            parent: 'restaurante-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restaurante/restaurante-my-suffix-dialog.html',
                    controller: 'RestauranteMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                direccion: null,
                                nombre: null,
                                ubicacionLat: null,
                                ubicacionLong: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('restaurante-my-suffix', null, { reload: 'restaurante-my-suffix' });
                }, function() {
                    $state.go('restaurante-my-suffix');
                });
            }]
        })
        .state('restaurante-my-suffix.edit', {
            parent: 'restaurante-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restaurante/restaurante-my-suffix-dialog.html',
                    controller: 'RestauranteMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Restaurante', function(Restaurante) {
                            return Restaurante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('restaurante-my-suffix', null, { reload: 'restaurante-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('restaurante-my-suffix.delete', {
            parent: 'restaurante-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restaurante/restaurante-my-suffix-delete-dialog.html',
                    controller: 'RestauranteMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Restaurante', function(Restaurante) {
                            return Restaurante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('restaurante-my-suffix', null, { reload: 'restaurante-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
