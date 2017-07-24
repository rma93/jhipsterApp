(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('RestauranteMySuffixDialogController', RestauranteMySuffixDialogController);

    RestauranteMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Restaurante'];

    function RestauranteMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Restaurante) {
        var vm = this;

        vm.restaurante = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.restaurante.id !== null) {
                Restaurante.update(vm.restaurante, onSaveSuccess, onSaveError);
            } else {
                Restaurante.save(vm.restaurante, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prueba3App:restauranteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
