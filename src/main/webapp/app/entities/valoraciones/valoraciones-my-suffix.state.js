(function() {
    'use strict';

    angular
        .module('prueba3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('valoraciones-my-suffix', {
            parent: 'entity',
            url: '/valoraciones-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.valoraciones.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoraciones/valoracionesmySuffix.html',
                    controller: 'ValoracionesMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoraciones');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('valoraciones-my-suffix-detail', {
            parent: 'valoraciones-my-suffix',
            url: '/valoraciones-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.valoraciones.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/valoraciones/valoraciones-my-suffix-detail.html',
                    controller: 'ValoracionesMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('valoraciones');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Valoraciones', function($stateParams, Valoraciones) {
                    return Valoraciones.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'valoraciones-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('valoraciones-my-suffix-detail.edit', {
            parent: 'valoraciones-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoraciones/valoraciones-my-suffix-dialog.html',
                    controller: 'ValoracionesMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Valoraciones', function(Valoraciones) {
                            return Valoraciones.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoraciones-my-suffix.new', {
            parent: 'valoraciones-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoraciones/valoraciones-my-suffix-dialog.html',
                    controller: 'ValoracionesMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                puntuacion: null,
                                comentario: null,
                                ubicacionLatRest: null,
                                ubicacionLongRest: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('valoraciones-my-suffix', null, { reload: 'valoraciones-my-suffix' });
                }, function() {
                    $state.go('valoraciones-my-suffix');
                });
            }]
        })
        .state('valoraciones-my-suffix.edit', {
            parent: 'valoraciones-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoraciones/valoraciones-my-suffix-dialog.html',
                    controller: 'ValoracionesMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Valoraciones', function(Valoraciones) {
                            return Valoraciones.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoraciones-my-suffix', null, { reload: 'valoraciones-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('valoraciones-my-suffix.delete', {
            parent: 'valoraciones-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/valoraciones/valoraciones-my-suffix-delete-dialog.html',
                    controller: 'ValoracionesMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Valoraciones', function(Valoraciones) {
                            return Valoraciones.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('valoraciones-my-suffix', null, { reload: 'valoraciones-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
