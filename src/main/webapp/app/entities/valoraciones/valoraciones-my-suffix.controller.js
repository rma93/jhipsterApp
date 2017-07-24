(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('ValoracionesMySuffixController', ValoracionesMySuffixController);

    ValoracionesMySuffixController.$inject = ['Valoraciones'];

    function ValoracionesMySuffixController(Valoraciones) {

        var vm = this;

        vm.valoraciones = [];

        loadAll();

        function loadAll() {
            Valoraciones.query(function(result) {
                vm.valoraciones = result;
                vm.searchQuery = null;
            });
        }
    }
})();
