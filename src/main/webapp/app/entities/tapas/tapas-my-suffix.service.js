(function() {
    'use strict';
    angular
        .module('prueba3App')
        .factory('Tapas', Tapas);

    Tapas.$inject = ['$resource'];

    function Tapas ($resource) {
        var resourceUrl =  'api/tapas/:id';

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
