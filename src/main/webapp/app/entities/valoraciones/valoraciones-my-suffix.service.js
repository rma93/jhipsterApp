(function() {
    'use strict';
    angular
        .module('prueba3App')
        .factory('Valoraciones', Valoraciones);

    Valoraciones.$inject = ['$resource'];

    function Valoraciones ($resource) {
        var resourceUrl =  'api/valoraciones/:id';

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
