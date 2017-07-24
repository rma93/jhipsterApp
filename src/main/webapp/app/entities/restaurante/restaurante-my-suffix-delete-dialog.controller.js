(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('RestauranteMySuffixDeleteController',RestauranteMySuffixDeleteController);

    RestauranteMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Restaurante'];

    function RestauranteMySuffixDeleteController($uibModalInstance, entity, Restaurante) {
        var vm = this;

        vm.restaurante = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Restaurante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
