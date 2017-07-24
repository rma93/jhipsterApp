(function() {
    'use strict';

    angular
        .module('prueba3App')
        .controller('UsuarioMySuffixDialogController', UsuarioMySuffixDialogController);

    UsuarioMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Usuario'];

    function UsuarioMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Usuario) {
        var vm = this;

        vm.usuario = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.usuario.id !== null) {
                Usuario.update(vm.usuario, onSaveSuccess, onSaveError);
            } else {
                Usuario.save(vm.usuario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('prueba3App:usuarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, usuario) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        usuario.foto = base64Data;
                        usuario.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
