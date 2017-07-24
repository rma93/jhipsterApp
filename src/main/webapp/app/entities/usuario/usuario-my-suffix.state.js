(function() {
    'use strict';

    angular
        .module('prueba3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('usuario-my-suffix', {
            parent: 'entity',
            url: '/usuario-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.usuario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usuario/usuariosmySuffix.html',
                    controller: 'UsuarioMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('usuario');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('usuario-my-suffix-detail', {
            parent: 'usuario-my-suffix',
            url: '/usuario-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prueba3App.usuario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usuario/usuario-my-suffix-detail.html',
                    controller: 'UsuarioMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('usuario');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Usuario', function($stateParams, Usuario) {
                    return Usuario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'usuario-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('usuario-my-suffix-detail.edit', {
            parent: 'usuario-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuario/usuario-my-suffix-dialog.html',
                    controller: 'UsuarioMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Usuario', function(Usuario) {
                            return Usuario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usuario-my-suffix.new', {
            parent: 'usuario-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuario/usuario-my-suffix-dialog.html',
                    controller: 'UsuarioMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido1: null,
                                apellido2: null,
                                edad: null,
                                email: null,
                                calle: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('usuario-my-suffix', null, { reload: 'usuario-my-suffix' });
                }, function() {
                    $state.go('usuario-my-suffix');
                });
            }]
        })
        .state('usuario-my-suffix.edit', {
            parent: 'usuario-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuario/usuario-my-suffix-dialog.html',
                    controller: 'UsuarioMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Usuario', function(Usuario) {
                            return Usuario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usuario-my-suffix', null, { reload: 'usuario-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usuario-my-suffix.delete', {
            parent: 'usuario-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuario/usuario-my-suffix-delete-dialog.html',
                    controller: 'UsuarioMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Usuario', function(Usuario) {
                            return Usuario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usuario-my-suffix', null, { reload: 'usuario-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
