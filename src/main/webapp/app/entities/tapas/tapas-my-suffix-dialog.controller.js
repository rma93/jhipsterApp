(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('TapasMySuffixDialogController', TapasMySuffixDialogController);

    TapasMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tapas'];

    function TapasMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tapas) {
        var vm = this;

        vm.tapas = entity;
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
            if (vm.tapas.id !== null) {
                Tapas.update(vm.tapas, onSaveSuccess, onSaveError);
            } else {
                Tapas.save(vm.tapas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prueba3App:tapasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
