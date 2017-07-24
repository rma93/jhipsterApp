(function() {
    'use strict';
    angular
        .module('prueba3App')
        .factory('Restaurante', Restaurante);

    Restaurante.$inject = ['$resource'];

    function Restaurante ($resource) {
        var resourceUrl =  'api/restaurantes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
