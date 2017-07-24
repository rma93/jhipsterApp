(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('ValoracionesMySuffixDialogController', ValoracionesMySuffixDialogController);

    ValoracionesMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Valoraciones'];

    function ValoracionesMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Valoraciones) {
        var vm = this;

        vm.valoraciones = entity;
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
            if (vm.valoraciones.id !== null) {
                Valoraciones.update(vm.valoraciones, onSaveSuccess, onSaveError);
            } else {
                Valoraciones.save(vm.valoraciones, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prueba3App:valoracionesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
