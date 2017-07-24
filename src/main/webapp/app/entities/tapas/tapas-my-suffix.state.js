(function() {
    'use strict';

    angular
        .module('prueba3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tapas-my-suffix', {
            parent: 'entity',
            url: '/tapas-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.tapas.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tapas/tapasmySuffix.html',
                    controller: 'TapasMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tapas');
                    $translatePartialLoader.addPart('alergenos');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tapas-my-suffix-detail', {
            parent: 'tapas-my-suffix',
            url: '/tapas-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.tapas.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tapas/tapas-my-suffix-detail.html',
                    controller: 'TapasMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tapas');
                    $translatePartialLoader.addPart('alergenos');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tapas', function($stateParams, Tapas) {
                    return Tapas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tapas-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tapas-my-suffix-detail.edit', {
            parent: 'tapas-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tapas/tapas-my-suffix-dialog.html',
                    controller: 'TapasMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tapas', function(Tapas) {
                            return Tapas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tapas-my-suffix.new', {
            parent: 'tapas-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tapas/tapas-my-suffix-dialog.html',
                    controller: 'TapasMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idtapa: null,
                                nombre: null,
                                ingrediente: null,
                                alergenos: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tapas-my-suffix', null, { reload: 'tapas-my-suffix' });
                }, function() {
                    $state.go('tapas-my-suffix');
                });
            }]
        })
        .state('tapas-my-suffix.edit', {
            parent: 'tapas-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tapas/tapas-my-suffix-dialog.html',
                    controller: 'TapasMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tapas', function(Tapas) {
                            return Tapas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tapas-my-suffix', null, { reload: 'tapas-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tapas-my-suffix.delete', {
            parent: 'tapas-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tapas/tapas-my-suffix-delete-dialog.html',
                    controller: 'TapasMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tapas', function(Tapas) {
                            return Tapas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tapas-my-suffix', null, { reload: 'tapas-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
